package com.ansh.api

import com.ansh.api.builders.GetBuilder
import com.ansh.api.builders.MultipartBuilder
import com.ansh.api.builders.PostBuilder

object ApiHelper {

    fun get(url: String) = GetBuilder.getBuilder(url)

    fun post(url: String) = PostBuilder.getJsonBuilder(url)

    fun formData(url: String) = PostBuilder.getFormDataBuilder(url)

    fun multipart(url: String) = MultipartBuilder.getBuilder(url)

}