package com.ansh.api.impl

import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @GET("{url}")
    fun getApi(
        @Path("url") url: String
    ): Call<JsonObject>

    @POST("{url}")
    fun postApi(
        @Path("url") url: String,
        @Body jsonObject: JsonObject
    ): Call<JsonObject>

    @POST("{url}")
    fun postApi(
        @Path("url") url: String,
        @PartMap bodyMap: Map<String, RequestBody>
    ): Call<JsonObject>

    @Multipart
    @POST("{url}")
    fun multipartApi(
        @Path("url") url: String,
        @PartMap fileMap: Map<String, MultipartBody.Part>,
        @PartMap bodyMap: Map<String, RequestBody>
    ): Call<JsonObject>

}