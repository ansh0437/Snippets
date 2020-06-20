package com.ansh.core.api.v4

import com.google.gson.Gson
import com.google.gson.JsonObject
import java.io.Serializable

data class Response(
    val success: Boolean,
    val error: String = "",
    val data: JsonObject? = null
) : Serializable {

    fun <T> getData(clazz: Class<T>): T {
        return Gson().fromJson(data, clazz)
    }

}