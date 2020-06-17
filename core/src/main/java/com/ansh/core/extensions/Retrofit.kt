package com.ansh.core.extensions

import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

val File.requestBody
    get() = RequestBody.create("*/*".toMediaTypeOrNull(), this)

val Int.requestBody
    get() = RequestBody.create("text/plain".toMediaTypeOrNull(), this.toString())

val String.requestBody
    get() = RequestBody.create("text/plain".toMediaTypeOrNull(), this)

fun RequestBody.multipartBody(key: String = "image", fileName: String = "image.jpg") =
    MultipartBody.Part.createFormData(key, fileName, this)
