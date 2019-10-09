package com.ansh.api

import com.ansh.api.builders.GetBuilder
import com.ansh.api.builders.MultipartBuilder
import com.ansh.api.builders.PostBuilder
import com.ansh.enums.ApiType

class ApiHelper private constructor() {

    companion object {
        fun get(url: String): GetBuilder {
            return ApiHelper().get(url)
        }

        fun post(url: String): PostBuilder {
            return ApiHelper().post(url)
        }

        fun formData(url: String): PostBuilder {
            return ApiHelper().formData(url)
        }

        fun multipart(url: String): MultipartBuilder {
            return ApiHelper().multipart(url)
        }
    }

    fun get(url: String): GetBuilder {
        return GetBuilder(url)
    }

    fun post(url: String): PostBuilder {
        return PostBuilder(url, ApiType.Post)
    }

    fun formData(url: String): PostBuilder {
        return PostBuilder(url, ApiType.FormData)
    }

    fun multipart(url: String): MultipartBuilder {
        return MultipartBuilder(url, ApiType.Multipart)
    }

}