package com.ansh.api

import com.ansh.BuildConfig
import com.ansh.api.impl.ApiInterface
import com.ansh.interfaces.ApiResponse
import com.google.gson.JsonObject
import okhttp3.Headers
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject

open class BaseApi {

    fun get() {
        Api.get(url = "")
            .setConnectionTimeout(timeout = 60L)
            .headers(headers = Headers.Builder().build())
            .bodyJson(json = JsonObject())
            .bodyFormData(formData = HashMap())
            .bodyMultipart(files = listOf(), formData = HashMap())
            .interceptLogs(intercept = BuildConfig.DEBUG)
            .addSuccessListener {}
            .addFailureListener {}
            .execute<JSONObject>()
    }

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