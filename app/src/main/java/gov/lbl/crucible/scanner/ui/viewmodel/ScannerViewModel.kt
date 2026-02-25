package gov.lbl.crucible.scanner.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gov.lbl.crucible.scanner.data.model.CrucibleResource
import gov.lbl.crucible.scanner.data.repository.CrucibleRepository
import gov.lbl.crucible.scanner.data.repository.ResourceResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class UiState {
    object Idle : UiState()
    object Loading : UiState()
    data class Success(val resource: CrucibleResource, val thumbnails: List<String> = emptyList()) : UiState()
    data class Error(val message: String) : UiState()
}

class ScannerViewModel : ViewModel() {
    private val repository = CrucibleRepository()

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun fetchResource(uuid: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            when (val result = repository.fetchResourceByUuid(uuid.trim())) {
                is ResourceResult.Success -> {
                    val resource = result.resource
                    // Fetch thumbnails if it's a dataset
                    val thumbnails = if (resource is gov.lbl.crucible.scanner.data.model.Dataset) {
                        println("DEBUG ViewModel: Fetching thumbnails for dataset ${resource.uniqueId}")
                        val fetched = repository.fetchThumbnails(resource.uniqueId)
                        println("DEBUG ViewModel: Got ${fetched.size} thumbnails")
                        fetched
                    } else {
                        println("DEBUG ViewModel: Resource is a sample, no thumbnails")
                        emptyList()
                    }
                    _uiState.value = UiState.Success(resource, thumbnails)
                }
                is ResourceResult.Error -> {
                    _uiState.value = UiState.Error(result.message)
                }
                is ResourceResult.Loading -> {
                    // Already in loading state
                }
            }
        }
    }

    fun reset() {
        _uiState.value = UiState.Idle
    }
}
