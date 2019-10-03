package com.ansh.api

import com.ansh.R
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
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface ApiRequest<T> {
    @GET("{url}")
    fun getApi(
        @Path("url") url: String
    ): Call<T>

    @POST("{url}")
    fun postApi(
        @Path("url") url: String,
        @Body jsonObject: JsonObject
    ): Call<T>

    @POST("{url}")
    fun postApi(
        @Path("url") url: String,
        @PartMap bodyMap: HashMap<String, RequestBody>
    ): Call<T>

    @Multipart
    @POST("{url}")
    fun multipartApi(
        @Path("url") url: String,
        @Part files: List<MultipartBody.Part>,
        @PartMap bodyMap: HashMap<String, RequestBody>
    ): Call<T>
}

enum class ApiType {
    Get, Post, FormData, Multipart
}

fun <T> Call<T>.execute(success: ((Any?) -> Unit)?, failure: ((String) -> Unit)?) {
    enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: retrofit2.Response<T>) {
            if (response.isSuccessful && response.body() != null) {
                success?.invoke(response.body())
            } else {
                failure?.invoke(R.string.connection_failed.resToStr)
            }
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            failure?.invoke(t.message ?: "")
        }
    })
}

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

    private var connectionTimeout: Long = 60
    private var readTimeout: Long = 60

    private val logging = HttpLoggingInterceptor()
    private val clientBuilder = OkHttpClient.Builder()

    private var requestHeaders: Headers = Headers.Builder().build()
    private var jsonObject: JsonObject? = null
    private var files: List<MultipartBody.Part>? = null
    private var formData: HashMap<String, RequestBody>? = null

    init {
        baseUrl = apiUrl.substring(0, apiUrl.lastIndexOf("/") + 1)
        requestUrl = apiUrl.substring(apiUrl.lastIndexOf("/") + 1, apiUrl.length)

        clientBuilder
            .connectTimeout(connectionTimeout, TimeUnit.SECONDS)
            .readTimeout(readTimeout, TimeUnit.SECONDS)
    }

    fun withHeaders(headers: Headers) = apply {
        requestHeaders = Headers.Builder().addAll(headers).build()
        clientBuilder.addInterceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .headers(requestHeaders)
                .method(original.method(), original.body())
                .build()
            chain.proceed(request)
        }
    }

    fun bodyJson(json: JsonObject) = apply {
        this.jsonObject = json
    }

    fun bodyFormData(formData: HashMap<String, RequestBody>) = apply {
        this.formData = formData
    }

    fun bodyMultipart(files: List<MultipartBody.Part>, formData: HashMap<String, RequestBody>) =
        apply {
            this.files = files
            this.formData = formData
        }

    fun interceptLogs(intercept: Boolean = true) = apply {
        logging.level =
            if (intercept) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        clientBuilder.addInterceptor(logging)
    }

    fun build(): Call<out Any?> {
        val apiRequest = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(clientBuilder.build())
            .build()
            .create(ApiRequest::class.java)

        return when (apiType) {
            ApiType.Get -> apiRequest.getApi(requestUrl)
            ApiType.Post -> apiRequest.postApi(requestUrl, jsonObject!!)
            ApiType.FormData -> apiRequest.postApi(requestUrl, formData!!)
            ApiType.Multipart -> apiRequest.multipartApi(requestUrl, files!!, formData!!)
        }
    }
}