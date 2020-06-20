package com.ansh.core.api.v1

import com.ansh.core.R
import com.ansh.core.extensions.resToStr
import com.ansh.core.interfaces.ApiResponse
import com.google.gson.JsonObject
import okhttp3.Headers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    fun <T> create(cls: Class<T>, requestHeaders: Headers = Headers.Builder().build()): T {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val headers = Headers.Builder().addAll(requestHeaders).build()

        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .headers(headers)
                    .method(original.method, original.body)
                    .build()
                chain.proceed(request)
            }
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(com.ansh.core.CoreApp.baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(cls)
    }

    fun <T> execute(call: Call<T>, apiResponse: ApiResponse?) {
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: retrofit2.Response<T>) {
                if (response.isSuccessful && response.body() != null) {
                    apiResponse?.onSuccess(response.body() as JsonObject)
                } else {
                    apiResponse?.onFailure(R.string.connection_failed.resToStr)
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                apiResponse?.onFailure(t.message ?: R.string.connection_failed.resToStr)
            }
        })
    }
}
