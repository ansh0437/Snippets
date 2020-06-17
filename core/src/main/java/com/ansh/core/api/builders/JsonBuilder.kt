package com.ansh.core.api.builders

import com.google.gson.JsonObject

class JsonBuilder private constructor() {

    companion object {
        fun add(key: String, value: String) = JsonBuilder().add(key, value)

        fun add(key: String, value: Int) = JsonBuilder().add(key, value)

        fun add(key: String, value: Float) = JsonBuilder().add(key, value)

        fun add(key: String, value: Double) = JsonBuilder().add(key, value)

        fun add(key: String, value: Long) = JsonBuilder().add(key, value)

        fun add(key: String, value: Boolean) = JsonBuilder().add(key, value)

        fun emptyJson() = JsonBuilder().emptyBuild()
    }

    private var jsonObject: JsonObject = JsonObject()

    fun add(key: String, value: String) = apply { jsonObject.addProperty(key, value) }

    fun add(key: String, value: Int) = apply { jsonObject.addProperty(key, value) }

    fun add(key: String, value: Float) = apply { jsonObject.addProperty(key, value) }

    fun add(key: String, value: Double) = apply { jsonObject.addProperty(key, value) }

    fun add(key: String, value: Long) = apply { jsonObject.addProperty(key, value) }

    fun add(key: String, value: Boolean) = apply { jsonObject.addProperty(key, value) }

    fun build(): JsonObject {
        if (jsonObject.isJsonNull) throw Exception("Json body cannot be empty")
        else return jsonObject
    }

    fun emptyBuild() = jsonObject

}