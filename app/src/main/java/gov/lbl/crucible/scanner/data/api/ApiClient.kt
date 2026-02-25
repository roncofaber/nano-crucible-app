package gov.lbl.crucible.scanner.data.api

import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    private const val DEFAULT_BASE_URL = "https://crucible.lbl.gov/api/v1/"

    private var apiKey: String? = null
    private var baseUrl: String = DEFAULT_BASE_URL
    private var _service: CrucibleApiService? = null

    fun setApiKey(key: String) {
        apiKey = key
    }

    fun setBaseUrl(url: String) {
        if (url != baseUrl) {
            baseUrl = url
            _service = null // Force recreation with new URL
        }
    }

    private val moshi = Moshi.Builder()
        .build()

    private val authInterceptor = Interceptor { chain ->
        val request = chain.request()
        val newRequest = if (apiKey != null) {
            request.newBuilder()
                .addHeader("Authorization", "Bearer $apiKey")
                .build()
        } else {
            request
        }
        chain.proceed(newRequest)
    }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    val service: CrucibleApiService
        get() {
            if (_service == null) {
                val okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(authInterceptor)
                    .addInterceptor(loggingInterceptor)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build()

                val retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient)
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .build()

                _service = retrofit.create(CrucibleApiService::class.java)
            }
            return _service!!
        }
}
