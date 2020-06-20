package com.ansh.core.api.v1

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

    @FormUrlEncoded
    @POST("{url}")
    fun postApi(
        @Path("url") url: String,
        @FieldMap bodyMap: HashMap<String, String>
    ): Call<JsonObject>

    @Multipart
    @POST("{url}")
    fun multipartApi(
        @Path("url") url: String,
        @Part files: List<MultipartBody.Part>,
        @PartMap bodyMap: HashMap<String, RequestBody>
    ): Call<JsonObject>

}