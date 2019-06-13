package com.ansh.interfaces

import com.google.gson.JsonObject

interface ApiResponse {

    fun onSuccess(jsonObject: JsonObject)

    fun onFailure(message: String)

}
