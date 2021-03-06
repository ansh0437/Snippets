package com.ansh.core.data.model

import okhttp3.logging.HttpLoggingInterceptor

data class ApiConfigDTO(
    val connectionTimeout: Long,
    val readTimeout: Long,
    val loggingInterceptor: HttpLoggingInterceptor
)