package com.ansh.core.api.builders

import com.ansh.core.data.model.ApiConfigDTO
import com.ansh.core.data.enums.ApiType
import okhttp3.Headers

class GetBuilder private constructor(url: String) : BaseBuilder() {

    companion object {
        fun getBuilder(url: String) = GetBuilder(url)
    }

    init {
        init(url, ApiType.Get)
    }

    fun configurations(config: ApiConfigDTO) = apply {
        configDTO = config
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