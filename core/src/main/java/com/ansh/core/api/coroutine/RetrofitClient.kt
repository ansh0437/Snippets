package com.ansh.core.api.coroutine

import com.ansh.core.BuildConfig
import com.ansh.core.CoreApp
import com.ansh.core.extensions.logError
import com.google.gson.JsonObject
import okhttp3.Headers
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

enum class ApiType{
    Get, Post, Multipart
}

const val SUCCESS_CODE = 200

class RetrofitClient private constructor(
    private val apiType: ApiType,
    private val baseUrl: String,
    private val customHeaders: Headers? = null
) {

    companion object {
        suspend fun get(
            url: String,
            customHeaders: Headers = Headers.Builder().build(),
            baseUrl: String = CoreApp.baseURL
        ) = RetrofitClient(ApiType.Get, baseUrl, customHeaders).execute(url)

        suspend fun post(
            url: String,
            json: JsonObject? = null,
            customHeaders: Headers = Headers.Builder().build(),
            baseUrl: String = CoreApp.baseURL
        ) = RetrofitClient(ApiType.Post, baseUrl, customHeaders).execute(url, json)

        suspend fun multipart(
            url: String,
            part: MultipartBody.Part? = null,
            customHeaders: Headers = Headers.Builder().build(),
            baseUrl: String = CoreApp.baseURL
        ) = RetrofitClient(ApiType.Multipart, baseUrl, customHeaders).execute(url, part)
    }

    private val tag = RetrofitClient::class.java.simpleName

    private val connectionTimeout = 120L
    private val readTimeout = 120L
    private val callTimeout = 120L

    suspend fun execute(url: String, body: Any? = null): JsonObject? {
        val client = getClient(RetrofitInterface::class.java)

        val response = when (apiType) {
            ApiType.Get -> client.get(url)
            ApiType.Post ->
                if (body != null) client.postJson(url, body as JsonObject)
                else client.post(url)
            ApiType.Multipart -> client.multipart(url, body as MultipartBody.Part)
        }

        if (response.isSuccessful && response.code() == SUCCESS_CODE) {
            try {
                if (response.body() != null) {
                    return response.body() as JsonObject
                }
            } catch (e: Exception) {
                e.logError(tag, "execute: ")
                return null
            }
        }
        return null
    }

    private fun <T> getClient(cls: Class<T>): T {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient())
            .build()
        return retrofit.create(cls)
    }

    private fun okHttpClient() = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .headers(buildHeaders())
                .method(original.method, original.body)
                .build()
            chain.proceed(request)
        }
        .connectTimeout(connectionTimeout, TimeUnit.SECONDS)
        .readTimeout(readTimeout, TimeUnit.SECONDS)
        .callTimeout(callTimeout, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor())
        .build()

    private fun httpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = when {
            apiType == ApiType.Multipart -> HttpLoggingInterceptor.Level.HEADERS
            BuildConfig.DEBUG -> HttpLoggingInterceptor.Level.BODY
            else -> HttpLoggingInterceptor.Level.NONE
        }
    }

    private fun buildHeaders() = Headers.Builder().apply {
        if (customHeaders != null) addAll(customHeaders)
    }.build()

}
