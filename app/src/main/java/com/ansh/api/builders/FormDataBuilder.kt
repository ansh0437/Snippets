package com.ansh.api.builders

import okhttp3.MediaType
import okhttp3.RequestBody

class FormDataBuilder private constructor() {

    companion object {
        fun add(key: String, value: String) = FormDataBuilder().add(key, value)

        fun emptyFormData() = FormDataBuilder().emptyBuild()
    }

    private var formData: HashMap<String, RequestBody> = HashMap()

    private fun requestBody(value: String) = RequestBody.create(MediaType.get("text/plain"), value)

    fun add(key: String, value: String) = apply { formData[key] = requestBody(value) }

    fun build(): HashMap<String, RequestBody> {
        if (formData.isEmpty()) throw Exception("Form Data cannot be empty")
        else return formData
    }

    fun emptyBuild() = formData

}