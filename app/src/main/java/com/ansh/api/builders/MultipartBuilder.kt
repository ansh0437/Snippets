package com.ansh.api.builders

import com.ansh.enums.ApiType
import okhttp3.Headers
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor

class MultipartBuilder constructor(url: String, apiType: ApiType) : BaseBuilder() {

    init {
        init(url, apiType)
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

    fun multipartFiles(files: List<MultipartBody.Part>) = apply {
        this.files = files
    }

    fun multipartBody(formData: HashMap<String, RequestBody>) = apply {
        this.formData = formData
    }

    fun addSuccessListener(listener: (Any?) -> Unit) = apply {
        successListener = listener
    }

    fun addFailureListener(listener: (String) -> Unit) = apply {
        failureListener = listener
    }

}