package com.ansh.api.builders

class FormDataBuilder private constructor() {

    companion object {
        fun add(key: String, value: String) = FormDataBuilder().add(key, value)

        fun emptyFormData() = FormDataBuilder().emptyBuild()
    }

    private var formData: HashMap<String, String> = HashMap()

    fun add(key: String, value: String) = apply { formData[key] = value }

    fun build(): HashMap<String, String> {
        if (formData.isEmpty()) throw Exception("Form Data cannot be empty")
        else return formData
    }

    fun emptyBuild() = formData

}