package com.ansh.core.api.coroutine

import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface RetrofitInterface {

    @GET("{url}")
    suspend fun get(
        @Path(value = "url", encoded = true) url: String
    ): Response<JsonObject>

    @POST("{url}")
    suspend fun post(
        @Path(value = "url", encoded = true) url: String
    ): Response<JsonObject>

    @FormUrlEncoded
    @POST("{url}")
    suspend fun postForm(
        @Path(value = "url", encoded = true) url: String,
        @FieldMap bodyMap: HashMap<String, String>
    ): Response<JsonObject>

    @POST("{url}")
    suspend fun postJson(
        @Path(value = "url", encoded = true) url: String,
        @Body jsonObject: JsonObject
    ): Response<JsonObject>

    @POST("{url}")
    suspend fun download(
        @Path(value = "url", encoded = true) url: String,
        @Body jsonObject: JsonObject
    ): Response<ResponseBody>

    @Multipart
    @POST("{url}")
    suspend fun multipart(
        @Path(value = "url", encoded = true) url: String,
        @Part file: MultipartBody.Part
    ): Response<JsonObject>

    @Multipart
    @POST("{url}")
    suspend fun multipart(
        @Path(value = "url", encoded = true) url: String,
        @Part file: MultipartBody.Part,
        @PartMap bodyMap: HashMap<String, RequestBody>
    ): Response<JsonObject>

    @Multipart
    @POST("{url}")
    suspend fun multipart(
        @Path(value = "url", encoded = true) url: String,
        @Part files: List<MultipartBody.Part>,
        @PartMap bodyMap: HashMap<String, RequestBody>
    ): Response<JsonObject>

}