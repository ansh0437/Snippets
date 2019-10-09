package com.ansh.api.builders

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.*

class FileBuilder private constructor() {

    companion object {
        fun addFile(key: String, file: File) = FileBuilder().addFile(key, file)
    }

    private val files = ArrayList<MultipartBody.Part>()

    private fun fileBody(file: File) = RequestBody.create(MediaType.get("*/*"), file)

    fun addFile(key: String, file: File) = apply {
        files.add(MultipartBody.Part.createFormData(key, file.name, fileBody(file)))
    }

    fun build(): ArrayList<MultipartBody.Part> {
        if (files.isEmpty()) throw Exception("No file added to upload")
        else return files
    }

}