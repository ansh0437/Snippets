package com.ansh.core.api.v1

import com.ansh.core.interfaces.ApiResponse
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody

open class BaseApi {

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

    fun formData(url: String, formData: HashMap<String, String>, apiResponse: ApiResponse?) {
        val apiInterface = ApiClient.create(ApiInterface::class.java)
        val call = apiInterface.postApi(url, formData)
        ApiClient.execute(call, apiResponse)
    }

    fun multipart(
        url: String,
        files: List<MultipartBody.Part>,
        bodyMap: HashMap<String, RequestBody>,
        apiResponse: ApiResponse?
    ) {
        val apiInterface = ApiClient.create(ApiInterface::class.java)
        val call = apiInterface.multipartApi(url, files, bodyMap)
        ApiClient.execute(call, apiResponse)
    }
}