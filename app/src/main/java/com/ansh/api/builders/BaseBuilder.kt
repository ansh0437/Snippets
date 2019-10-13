package com.ansh.api.builders

import com.ansh.R
import com.ansh.api.impl.ApiRequest
import com.ansh.data.model.ApiConfigDTO
import com.ansh.enums.ApiType
import com.ansh.extensions.resToStr
import com.google.gson.JsonObject
import okhttp3.Headers
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

open class BaseBuilder {

    private lateinit var baseUrl: String
    private lateinit var requestUrl: String
    private lateinit var apiType: ApiType

    internal var configDTO: ApiConfigDTO = ConfigBuilder.getBuilder().build()
    internal var requestHeaders: Headers = Headers.Builder().build()
    internal var jsonObject: JsonObject? = null
    internal var formData: HashMap<String, String>? = null
    internal var multipartFiles: List<MultipartBody.Part>? = null
    internal var multipartFields: HashMap<String, RequestBody>? = null

    internal var successListener: ((Any?) -> Unit)? = null
    internal var failureListener: ((String) -> Unit)? = null

    fun init(apiUrl: String, apiType: ApiType) {
        baseUrl = apiUrl.substring(0, apiUrl.lastIndexOf("/") + 1)
        requestUrl = apiUrl.substring(apiUrl.lastIndexOf("/") + 1, apiUrl.length)

        this.apiType = apiType
    }

    open fun <T> execute() {
        val apiRequest = getRetrofit().create(ApiRequest::class.java)

        val call: Call<T> = when (apiType) {
            ApiType.Get -> {
                apiRequest.getApi(requestUrl, requestHeaders)
            }
            ApiType.Post -> {
                apiRequest.postApi(requestUrl, jsonObject!!)
            }
            ApiType.FormData -> {
                apiRequest.postApi(requestUrl, formData!!)
            }
            ApiType.Multipart -> {
                apiRequest.multipartApi(requestUrl, multipartFiles!!, multipartFields!!)
            }
        }

        callApi(call)
    }

    private fun <T> callApi(call: Call<T>) {
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: retrofit2.Response<T>) {
                if (response.isSuccessful && response.body() != null) {
                    successListener?.invoke(response.body())
                } else {
                    failureListener?.invoke(R.string.connection_failed.resToStr)
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                failureListener?.invoke(t.message ?: "")
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
            .connectTimeout(configDTO.connectionTimeout, TimeUnit.SECONDS)
            .readTimeout(configDTO.readTimeout, TimeUnit.SECONDS)
            .addInterceptor(configDTO.loggingInterceptor)
            .build()
    }

}