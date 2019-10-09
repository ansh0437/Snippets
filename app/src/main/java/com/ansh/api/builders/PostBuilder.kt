package com.ansh.api.builders

import com.ansh.enums.ApiType
import com.google.gson.JsonObject
import okhttp3.Headers
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor

class PostBuilder constructor(url: String, apiType: ApiType) : BaseBuilder() {

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

    fun bodyJson(json: JsonObject) = apply {
        this.jsonObject = json
    }

    fun bodyFormData(formData: HashMap<String, RequestBody>) = apply {
        this.formData = formData
    }

    fun addSuccessListener(listener: (Any?) -> Unit) = apply {
        successListener = listener
    }

    fun addFailureListener(listener: (String) -> Unit) = apply {
        failureListener = listener
    }

}