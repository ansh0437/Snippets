package com.ansh.core.api.v5

import com.ansh.core.CoreApp
import com.ansh.core.data.enums.ApiType
import okhttp3.Headers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient<B, R> private constructor(
    private val apiType: ApiType,
    private val baseUrl: String = CoreApp.baseURL,
    private val apiUrl: String,
    private val customHeaders: Headers = Headers.Builder().build(),
    private val requestBody: B
) {

    companion object {

        fun <B, R> get(
            baseUrl: String = CoreApp.baseURL,
            apiUrl: String,
            customHeaders: Headers = Headers.Builder().build(),
            requestBody: B
        ) = ApiClient<B, R>(ApiType.Get, baseUrl, apiUrl, customHeaders, requestBody)

        fun <B, R> post(
            baseUrl: String = CoreApp.baseURL,
            apiUrl: String,
            customHeaders: Headers = Headers.Builder().build(),
            requestBody: B
        ) = ApiClient<B, R>(ApiType.Post, baseUrl, apiUrl, customHeaders, requestBody)

    }

    fun execute(
        successCallback: (R) -> Unit,
        failureCallback: (String) -> Unit
    ) {
        val retrofit = getClient(baseUrl, customHeaders, ApiRequest::class.java)

        val call: Call<R> = when (apiType) {
            ApiType.Get -> retrofit.getApi(apiUrl, requestBody)
            ApiType.Post -> retrofit.postApi(apiUrl, requestBody)
            ApiType.FormData -> TODO()
            ApiType.Multipart -> TODO()
        }

        call.enqueue(object : Callback<R> {
            override fun onResponse(call: Call<R>, response: Response<R>) {
                if (response.body() != null && response.isSuccessful) {
                    successCallback(response.body()!!)
                } else {
                    failureCallback(response.message())
                }
            }

            override fun onFailure(call: Call<R>, t: Throwable) {
                failureCallback(t.toString())
            }
        })
    }

    private fun <T> getClient(
        baseUrl: String,
        customHeaders: Headers,
        cls: Class<T>
    ): T {
        val retrofit = Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient(customHeaders))
            .build()
        return retrofit.create(cls)
    }

    private fun okHttpClient(customHeaders: Headers) = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .headers(buildHeaders(customHeaders))
                .method(original.method, original.body)
                .build()
            chain.proceed(request)
        }
        .connectTimeout(120, TimeUnit.SECONDS)
        .readTimeout(120, TimeUnit.SECONDS)
        .callTimeout(120, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor())
        .build()

    private fun httpLoggingInterceptor() =
        HttpLoggingInterceptor().apply {
            level = if (CoreApp.debuggingEnabled) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }

    private fun buildHeaders(customHeaders: Headers) =
        Headers.Builder().apply { addAll(customHeaders) }.build()

}