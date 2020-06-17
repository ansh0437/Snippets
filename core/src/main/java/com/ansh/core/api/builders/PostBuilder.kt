package com.ansh.core.api.builders

import com.ansh.core.data.model.ApiConfigDTO
import com.ansh.core.data.enums.ApiType
import com.google.gson.JsonObject
import okhttp3.Headers

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

    fun bodyFormData(formData: HashMap<String, String>) = apply {
        this.formData = formData
    }

    fun addSuccessListener(listener: (Any?) -> Unit) = apply {
        successListener = listener
    }

    fun addFailureListener(listener: (String) -> Unit) = apply {
        failureListener = listener
    }

}