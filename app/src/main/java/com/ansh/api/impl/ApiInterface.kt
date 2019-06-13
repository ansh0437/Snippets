package com.ansh.api.impl

import com.google.gson.JsonObject
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @GET("{url}")
    fun getApi(@Path("url") url: String): Call<JsonObject>

    @POST("{url}")
    fun postApi(@Path("url") url: String, @Body jsonObject: JsonObject): Call<JsonObject>

    @Multipart
    @POST("{url}")
    fun multipartApi(@Path("url") url: String, @Part image: MultipartBody.Part): Call<JsonObject>

}