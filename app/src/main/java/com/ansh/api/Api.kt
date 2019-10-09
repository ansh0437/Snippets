package com.ansh.api

import com.ansh.R
import com.ansh.api.impl.ApiRequest
import com.ansh.enums.ApiType
import com.ansh.extensions.resToStr
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

class Api private constructor(apiUrl: String, private val apiType: ApiType) {

    companion object {
        fun get(url: String): Api {
            return Api(url, ApiType.Get)
        }

        fun post(url: String): Api {
            return Api(url, ApiType.Post)
        }

        fun formData(url: String): Api {
            return Api(url, ApiType.FormData)
        }

        fun multipart(url: String): Api {
            return Api(url, ApiType.Multipart)
        }
    }

    private var baseUrl = ""
    private var requestUrl = ""

    private var connectionTimeout: Long = 60L
    private var readTimeout: Long = 60L

    private val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private var requestHeaders: Headers = Headers.Builder().build()
    private var jsonObject: JsonObject? = null
    private var files: List<MultipartBody.Part>? = null
    private var formData: HashMap<String, RequestBody>? = null

    private var successListener: ((Any?) -> Unit)? = null
    private var failureListener: ((String) -> Unit)? = null

    init {
        baseUrl = apiUrl.substring(0, apiUrl.lastIndexOf("/") + 1)
        requestUrl = apiUrl.substring(apiUrl.lastIndexOf("/") + 1, apiUrl.length)
    }

    fun setConnectionTimeout(timeout: Long) = apply {
        connectionTimeout = timeout
    }

    fun headers(headers: Headers) = apply {
        requestHeaders = headers
    }

    fun bodyJson(json: JsonObject) = apply {
        this.jsonObject = json
    }

    fun bodyFormData(formData: HashMap<String, RequestBody>) = apply {
        this.formData = formData
    }

    fun bodyMultipart(
        files: List<MultipartBody.Part>,
        formData: HashMap<String, RequestBody>?
    ) = apply {
        this.files = files
        this.formData = formData
    }

    fun interceptLogs(intercept: Boolean = true) = apply {
        logging.level =
            if (intercept) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
    }

    fun addSuccessListener(listener: (Any?) -> Unit) = apply {
        successListener = listener
    }

    fun addFailureListener(listener: (String) -> Unit) = apply {
        failureListener = listener
    }

    fun <T> execute() {
        val apiRequest = getRetrofit().create(ApiRequest::class.java)

        val call: Call<T> = when (apiType) {
            ApiType.Get -> apiRequest.getApi(requestUrl, requestHeaders)
            ApiType.Post -> apiRequest.postApi(requestUrl, jsonObject!!)
            ApiType.FormData -> apiRequest.postApi(requestUrl, formData!!)
            ApiType.Multipart -> apiRequest.multipartApi(requestUrl, files!!, formData!!)
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
            .connectTimeout(connectionTimeout, TimeUnit.SECONDS)
            .readTimeout(readTimeout, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()
    }

}