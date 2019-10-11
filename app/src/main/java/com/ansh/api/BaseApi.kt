package com.ansh.api

import com.ansh.BuildConfig
import com.ansh.api.builders.*
import com.ansh.api.impl.ApiInterface
import com.ansh.interfaces.ApiResponse
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

open class BaseApi {

    fun sample() {
        val configDTO = ConfigBuilder
            .getBuilder()
            .connectionTimeout(60L)
            .readTimeout(60L)
            .interceptLogs(true)
            .build()
        val headers = HeaderBuilder.addHeader("", "").build()
        val json = JsonBuilder.add("", "").build()
        val formData = FormDataBuilder.add("", "").build()
        val multipartFiles = FileBuilder.addFile("", File("")).build()

        ApiHelper.get("")
            .configurations(configDTO)
            .addHeaders(headers)
            .addSuccessListener { }
            .addFailureListener { }
            .execute<JsonObject>()

        ApiHelper
            .post("")
            .configurations(configDTO)
            .addHeaders(headers)
            .bodyJson(json)
            .addSuccessListener { }
            .addFailureListener { }
            .execute<JsonObject>()

        ApiHelper
            .formData("")
            .configurations(configDTO)
            .addHeaders(headers)
            .bodyFormData(formData)
            .addSuccessListener { }
            .addFailureListener { }
            .execute<JsonObject>()

        ApiHelper
            .multipart("")
            .configurations(configDTO)
            .addHeaders(headers)
            .multipartFiles(multipartFiles)
            .multipartBody(formData)
            .addSuccessListener { }
            .addFailureListener { }
            .execute<JsonObject>()

        Api.get("")
            .setConnectionTimeout(60L)
            .headers(headers)
            .bodyJson(json)
            .bodyFormData(formData)
            .bodyMultipart(multipartFiles, formData)
            .interceptLogs(BuildConfig.DEBUG)
            .addSuccessListener {}
            .addFailureListener {}
            .execute<JsonObject>()
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