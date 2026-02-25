package gov.lbl.crucible.scanner.data.repository

import gov.lbl.crucible.scanner.data.api.ApiClient
import gov.lbl.crucible.scanner.data.model.CrucibleResource
import gov.lbl.crucible.scanner.data.model.Dataset
import gov.lbl.crucible.scanner.data.model.DatasetReference
import gov.lbl.crucible.scanner.data.model.Sample
import gov.lbl.crucible.scanner.data.model.SampleReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

sealed class ResourceResult {
    data class Success(val resource: CrucibleResource) : ResourceResult()
    data class Error(val message: String) : ResourceResult()
    object Loading : ResourceResult()
}

class CrucibleRepository {
    private val api = ApiClient.service

    suspend fun fetchResourceByUuid(uuid: String): ResourceResult = withContext(Dispatchers.IO) {
        try {
            // First, determine the resource type using the new /idtype endpoint
            val typeResponse = api.getResourceType(uuid)

            if (!typeResponse.isSuccessful || typeResponse.body() == null) {
                // Fallback to old method if idtype endpoint doesn't work
                return@withContext fetchResourceByUuidFallback(uuid)
            }

            val resourceType = typeResponse.body()!!.objectType

            // Fetch the appropriate resource based on type
            when (resourceType.lowercase()) {
                "sample" -> {
                    val sampleResponse = api.getSample(uuid)
                    if (sampleResponse.isSuccessful && sampleResponse.body() != null) {
                        var sample = sampleResponse.body()!!

                        // Fetch parent samples
                        val parentsResponse = api.getParentSamples(uuid)
                        if (parentsResponse.isSuccessful && parentsResponse.body() != null) {
                            sample = sample.copy(
                                parentSamples = parentsResponse.body()!!
                                    .map { SampleReference(it.uniqueId, it.name) }
                                    .distinctBy { it.uniqueId }
                                    .sortedBy { it.uniqueId }
                            )
                        }

                        // Fetch child samples
                        val childrenResponse = api.getChildSamples(uuid)
                        if (childrenResponse.isSuccessful && childrenResponse.body() != null) {
                            sample.childSamples = childrenResponse.body()!!
                                .map { SampleReference(it.uniqueId, it.name) }
                                .distinctBy { it.uniqueId }
                                .sortedBy { it.uniqueId }
                        }

                        return@withContext ResourceResult.Success(sample)
                    }
                }
                "dataset" -> {
                    val datasetResponse = api.getDataset(uuid)
                    if (datasetResponse.isSuccessful && datasetResponse.body() != null) {
                        var dataset = datasetResponse.body()!!

                        // Fetch scientific metadata
                        val metadataResponse = api.getScientificMetadata(uuid)
                        if (metadataResponse.isSuccessful) {
                            dataset = dataset.copy(scientificMetadata = metadataResponse.body())
                        }

                        // Fetch parent datasets
                        val parentsResponse = api.getParentDatasets(uuid)
                        if (parentsResponse.isSuccessful && parentsResponse.body() != null) {
                            dataset.parentDatasets = parentsResponse.body()!!
                                .map {
                                    DatasetReference(it.uniqueId, it.name, it.measurement)
                                }
                                .distinctBy { it.uniqueId }
                                .sortedBy { it.uniqueId }
                        }

                        // Fetch child datasets
                        val childrenResponse = api.getChildDatasets(uuid)
                        if (childrenResponse.isSuccessful && childrenResponse.body() != null) {
                            dataset.childDatasets = childrenResponse.body()!!
                                .map {
                                    DatasetReference(it.uniqueId, it.name, it.measurement)
                                }
                                .distinctBy { it.uniqueId }
                                .sortedBy { it.uniqueId }
                        }

                        // Fetch samples associated with this dataset
                        val samplesResponse = api.getDatasetSamples(uuid)
                        if (samplesResponse.isSuccessful && samplesResponse.body() != null) {
                            dataset = dataset.copy(
                                samples = samplesResponse.body()!!
                                    .map {
                                        SampleReference(it.uniqueId, it.name)
                                    }
                                    .distinctBy { it.uniqueId }
                                    .sortedBy { it.uniqueId }
                            )
                        }

                        return@withContext ResourceResult.Success(dataset)
                    }
                }
            }

            ResourceResult.Error("Resource not found: $uuid")
        } catch (e: Exception) {
            ResourceResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    // Fallback method using the old approach (try both endpoints)
    private suspend fun fetchResourceByUuidFallback(uuid: String): ResourceResult {
        try {
            // Try sample first
            val sampleResponse = api.getSample(uuid)
            if (sampleResponse.isSuccessful && sampleResponse.body() != null) {
                return ResourceResult.Success(sampleResponse.body()!!)
            }

            // If not a sample, try dataset
            val datasetResponse = api.getDataset(uuid)
            if (datasetResponse.isSuccessful && datasetResponse.body() != null) {
                val dataset = datasetResponse.body()!!

                // Try to fetch scientific metadata
                val metadataResponse = api.getScientificMetadata(uuid)
                val enrichedDataset = if (metadataResponse.isSuccessful) {
                    dataset.copy(scientificMetadata = metadataResponse.body())
                } else {
                    dataset
                }

                return ResourceResult.Success(enrichedDataset)
            }

            // Neither worked
            return ResourceResult.Error("Resource not found: $uuid")
        } catch (e: Exception) {
            return ResourceResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    suspend fun fetchThumbnails(datasetUuid: String): List<String> = withContext(Dispatchers.IO) {
        try {
            val response = api.getThumbnails(datasetUuid)
            if (response.isSuccessful && response.body() != null) {
                val thumbnails = response.body()!!
                println("DEBUG: Fetched ${thumbnails.size} thumbnails for dataset $datasetUuid")
                thumbnails.mapIndexed { index, thumb ->
                    val dataUri = "data:image/png;base64,${thumb.thumbnailB64}"
                    println("DEBUG: Thumbnail $index length: ${thumb.thumbnailB64.length} chars, URI preview: ${dataUri.take(100)}")
                    dataUri
                }
            } else {
                println("DEBUG: Thumbnail fetch failed for $datasetUuid - Code: ${response.code()}, Message: ${response.message()}")
                emptyList()
            }
        } catch (e: Exception) {
            println("DEBUG: Exception fetching thumbnails for $datasetUuid: ${e.message}")
            e.printStackTrace()
            emptyList()
        }
    }
}
