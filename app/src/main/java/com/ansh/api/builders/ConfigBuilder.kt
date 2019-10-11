package com.ansh.api.builders

import com.ansh.data.model.ApiConfigDTO
import okhttp3.logging.HttpLoggingInterceptor

class ConfigBuilder private constructor() {

    companion object {
        fun getBuilder() = ConfigBuilder()
    }

    private var connectionTimeout: Long = 60L

    private var readTimeout: Long = 60L

    private val loggingInterceptor: HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    fun connectionTimeout(timeout: Long) = apply {
        connectionTimeout = timeout
    }

    fun readTimeout(timeout: Long) = apply {
        readTimeout = timeout
    }

    fun interceptLogs(intercept: Boolean) = apply {
        loggingInterceptor.level =
            if (intercept) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
    }

    fun build() = ApiConfigDTO(connectionTimeout, readTimeout, loggingInterceptor)

}