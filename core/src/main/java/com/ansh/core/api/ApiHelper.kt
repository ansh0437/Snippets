package com.ansh.core.api

import com.ansh.core.api.builders.GetBuilder
import com.ansh.core.api.builders.MultipartBuilder
import com.ansh.core.api.builders.PostBuilder

object ApiHelper {

    fun get(url: String) = GetBuilder.getBuilder(url)

    fun post(url: String) = PostBuilder.getJsonBuilder(url)

    fun formData(url: String) = PostBuilder.getFormDataBuilder(url)

    fun multipart(url: String) = MultipartBuilder.getBuilder(url)

}