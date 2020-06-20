package com.ansh.activities

import com.ansh.core.api.builders.*
import com.ansh.core.api.v4.ApiClient
import java.io.File

data class Abc(
    val status: Int
)

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
    val multipartFields = MultipartDataBuilder.addField("", "").buildFields()
    val multipartFiles = MultipartDataBuilder.addFile("", File("")).buildFiles()

    ApiClient
        .post("")
        .bodyFormData(hashMapOf())
        .interceptLogs(true)
        .execute { response ->
            response.getData(Abc::class.java)
        }

//    ApiHelper.get("")
//        .configurations(configDTO)
//        .addHeaders(headers)
//        .addSuccessListener { }
//        .addFailureListener { }
//        .execute<JsonObject>()
//
//    ApiHelper
//        .post("")
//        .configurations(configDTO)
//        .addHeaders(headers)
//        .bodyJson(json)
//        .addSuccessListener { }
//        .addFailureListener { }
//        .execute<JsonObject>()
//
//    ApiHelper
//        .formData("")
//        .configurations(configDTO)
//        .addHeaders(headers)
//        .bodyFormData(formData)
//        .addSuccessListener { }
//        .addFailureListener { }
//        .execute<JsonObject>()
//
//    ApiHelper
//        .multipart("")
//        .configurations(configDTO)
//        .addHeaders(headers)
//        .multipartFiles(multipartFiles)
//        .multipartFields(multipartFields)
//        .addSuccessListener { }
//        .addFailureListener { }
//        .execute<JsonObject>()
//
//    Api.get("")
//        .setConnectionTimeout(60L)
//        .headers(headers)
//        .bodyJson(json)
//        .bodyFormData(formData)
//        .bodyMultipart(multipartFiles, multipartFields)
//        .interceptLogs(BuildConfig.DEBUG)
//        .addSuccessListener {}
//        .addFailureListener {}
//        .execute<JsonObject>()
}