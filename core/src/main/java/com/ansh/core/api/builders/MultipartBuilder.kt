package com.ansh.core.api.builders

import com.ansh.core.data.model.ApiConfigDTO
import com.ansh.core.data.enums.ApiType
import okhttp3.Headers
import okhttp3.MultipartBody
import okhttp3.RequestBody

class MultipartBuilder private constructor(url: String) : BaseBuilder() {

    companion object {
        fun getBuilder(url: String) = MultipartBuilder(url)
    }

    init {
        init(url, ApiType.Multipart)
    }

    fun configurations(config: ApiConfigDTO) = apply {
        configDTO = config
    }

    fun addHeaders(headers: Headers) = apply {
        requestHeaders = headers
    }

    fun multipartFiles(files: List<MultipartBody.Part>) = apply {
        this.multipartFiles = files
    }

    fun multipartFields(formData: HashMap<String, RequestBody>) = apply {
        this.multipartFields = formData
    }

    fun addSuccessListener(listener: (Any?) -> Unit) = apply {
        successListener = listener
    }

    fun addFailureListener(listener: (String) -> Unit) = apply {
        failureListener = listener
    }

}