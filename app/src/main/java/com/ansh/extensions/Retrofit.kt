package com.ansh.extensions

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

val File.requestBody
    get() = RequestBody.create(MediaType.parse("*/*"), this)

val Int.requestBody
    get() = RequestBody.create(MediaType.parse("text/plain"), this.toString())

val String.requestBody
    get() = RequestBody.create(MediaType.parse("text/plain"), this)

fun RequestBody.multipartBody(key: String = "image", fileName: String = "image.jpg") =
    MultipartBody.Part.createFormData(key, fileName, this)
