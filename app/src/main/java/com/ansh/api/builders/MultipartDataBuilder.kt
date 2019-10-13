package com.ansh.api.builders

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.*

class MultipartDataBuilder private constructor() {

    companion object {
        fun addFile(key: String, file: File) = MultipartDataBuilder().addFile(key, file)

        fun addField(key: String, value: String) = MultipartDataBuilder().addField(key, value)
    }

    private val files = ArrayList<MultipartBody.Part>()
    private var formData: HashMap<String, RequestBody> = HashMap()

    private fun requestBody(value: String) = RequestBody.create(MediaType.get("text/plain"), value)

    private fun fileBody(file: File) = RequestBody.create(MediaType.get("*/*"), file)

    fun addFile(key: String, file: File) = apply {
        files.add(MultipartBody.Part.createFormData(key, file.name, fileBody(file)))
    }

    fun addField(key: String, value: String) = apply { formData[key] = requestBody(value) }

    fun buildFiles(): ArrayList<MultipartBody.Part> {
        if (files.isEmpty()) throw Exception("No file added to upload")
        else return files
    }

    fun buildFields(): HashMap<String, RequestBody>{
        if (formData.isEmpty()) throw Exception("No fields added")
        else return formData
    }

}