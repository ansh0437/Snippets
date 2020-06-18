package com.ansh.core.extensions

import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

val File.requestBody
    get() = this.asRequestBody("*/*".toMediaTypeOrNull())

val Int.requestBody
    get() = this.toString().toRequestBody("text/plain".toMediaTypeOrNull())

val String.requestBody
    get() = this.toRequestBody("text/plain".toMediaTypeOrNull())

fun RequestBody.multipartBody(key: String = "image", fileName: String = "image.jpg") =
    MultipartBody.Part.createFormData(key, fileName, this)
