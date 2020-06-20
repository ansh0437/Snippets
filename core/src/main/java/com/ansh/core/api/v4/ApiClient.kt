package com.ansh.core.api.v4

import com.ansh.core.CoreApp
import com.ansh.core.R
import com.ansh.core.data.enums.ApiType
import com.ansh.core.extensions.resToStr
import com.google.gson.JsonObject
import okhttp3.Headers
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient private constructor(
    private val baseUrl: String,
    private val requestUrl: String,
    private val apiType: ApiType
) {

    companion object {
        fun get(url: String, baseUrl: String = CoreApp.baseURL): ApiClient {
            return ApiClient(baseUrl, url, ApiType.Get)
        }

        fun post(url: String, baseUrl: String = CoreApp.baseURL): ApiClient {
            return ApiClient(baseUrl, url, ApiType.Post)
        }

        fun formData(url: String, baseUrl: String = CoreApp.baseURL): ApiClient {
            return ApiClient(baseUrl, url, ApiType.FormData)
        }

        fun multipart(url: String, baseUrl: String = CoreApp.baseURL): ApiClient {
            return ApiClient(baseUrl, url, ApiType.Multipart)
        }
    }

    private var connectionTimeout: Long = 120L
    private var readTimeout: Long = 120L
    private var callTimeout: Long = 120L

    private var loggingEnabled = CoreApp.debuggingEnabled

    private var requestHeaders: Headers = Headers.Builder().build()
    private var jsonObject: JsonObject? = null
    private var formData: HashMap<String, String>? = null
    private var multipartFiles: List<MultipartBody.Part>? = null
    private var multipartFields: HashMap<String, RequestBody>? = null

    fun setConnectionTimeout(timeout: Long) = apply {
        connectionTimeout = timeout
    }

    fun headers(headers: Headers) = apply {
        requestHeaders = headers
    }

    fun bodyJson(json: JsonObject) = apply {
        this.jsonObject = json
    }

    fun bodyFormData(formData: HashMap<String, String>) = apply {
        this.formData = formData
    }

    fun bodyMultipart(
        files: List<MultipartBody.Part>,
        fields: HashMap<String, RequestBody>?
    ) = apply {
        this.multipartFiles = files
        this.multipartFields = fields
    }

    fun interceptLogs(enable: Boolean) = apply {
        loggingEnabled = enable
    }

    fun execute(listener: (Response) -> Unit) {
        val apiRequest = getRetrofit().create(ApiRequest::class.java)

        val call: Call<JsonObject> = when (apiType) {
            ApiType.Get -> apiRequest.getApi(requestUrl, requestHeaders)
            ApiType.Post -> apiRequest.postApi(requestUrl, jsonObject!!)
            ApiType.FormData -> apiRequest.postApi(requestUrl, formData!!)
            ApiType.Multipart -> apiRequest.multipartApi(
                requestUrl,
                multipartFiles!!,
                multipartFields!!
            )
        }

        callApi(call, listener)
    }

    private fun callApi(
        call: Call<JsonObject>,
        listener: (Response) -> Unit
    ) {
        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(
                call: Call<JsonObject>,
                response: retrofit2.Response<JsonObject>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    listener(Response(true, data = response.body()))
                } else {
                    listener(
                        Response(
                            false,
                            response.message() ?: R.string.connection_failed.resToStr
                        )
                    )
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                listener(Response(false, t.message ?: R.string.connection_failed.resToStr))
            }
        })
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHttpClient())
            .build()
    }

    private fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(connectionTimeout, TimeUnit.SECONDS)
            .readTimeout(readTimeout, TimeUnit.SECONDS)
            .callTimeout(callTimeout, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor())
            .build()
    }

    private fun httpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = if (loggingEnabled) {
            when (apiType) {
                ApiType.Multipart -> HttpLoggingInterceptor.Level.HEADERS
                else -> HttpLoggingInterceptor.Level.BODY
            }
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

}