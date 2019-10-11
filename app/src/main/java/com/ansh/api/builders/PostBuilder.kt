package com.ansh.api.builders

import com.ansh.data.model.ApiConfigDTO
import com.ansh.enums.ApiType
import com.google.gson.JsonObject
import okhttp3.Headers
import okhttp3.RequestBody

class PostBuilder private constructor(url: String, apiType: ApiType) : BaseBuilder() {

    companion object {
        fun getJsonBuilder(url: String) = PostBuilder(url, ApiType.Post)

        fun getFormDataBuilder(url: String) = PostBuilder(url, ApiType.FormData)
    }

    init {
        init(url, apiType)
    }

    fun configurations(config: ApiConfigDTO) = apply {
        configDTO = config
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