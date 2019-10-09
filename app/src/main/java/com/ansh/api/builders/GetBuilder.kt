package com.ansh.api.builders

import com.ansh.enums.ApiType
import okhttp3.Headers
import okhttp3.logging.HttpLoggingInterceptor

class GetBuilder constructor(url: String) : BaseBuilder() {

    init {
        init(url, ApiType.Get)
    }

    fun connectionTimeout(timeout: Long) = apply {
        connectionTimeout = timeout
    }

    fun interceptLogs(intercept: Boolean) = apply {
        logging.level =
            if (intercept) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
    }

    fun addHeaders(headers: Headers) = apply {
        requestHeaders = headers
    }

    fun addSuccessListener(listener: (Any?) -> Unit) = apply {
        successListener = listener
    }

    fun addFailureListener(listener: (String) -> Unit) = apply {
        failureListener = listener
    }

}