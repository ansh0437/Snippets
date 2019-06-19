package com.ansh.data.repository

import com.ansh.api.ApiClient
import com.ansh.api.impl.ApiInterface
import com.ansh.interfaces.ApiResponse
import com.google.gson.JsonObject

open class BaseRepo {

    fun get(url: String, apiResponse: ApiResponse?) {
        val apiInterface = ApiClient.create(ApiInterface::class.java)
        val call = apiInterface.getApi(url)
        ApiClient.execute(call, apiResponse)
    }

    fun post(url: String, json: JsonObject, apiResponse: ApiResponse?) {
        val apiInterface = ApiClient.create(ApiInterface::class.java)
        val call = apiInterface.postApi(url, json)
        ApiClient.execute(call, apiResponse)
    }

}