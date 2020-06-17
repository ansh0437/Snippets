package com.ansh.core.api.impl

import com.google.gson.JsonObject
import okhttp3.Headers
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiRequest {

    @GET("{url}")
    fun <T> getApi(
        @Path("url") url: String,
        @HeaderMap headers: Headers
    ): Call<T>

    @POST("{url}")
    fun <T> postApi(
        @Path("url") url: String,
        @Body jsonObject: JsonObject
    ): Call<T>

    @FormUrlEncoded
    @POST("{url}")
    fun <T> postApi(
        @Path("url") url: String,
        @FieldMap bodyMap: HashMap<String, String>
    ): Call<T>

    @Multipart
    @POST("{url}")
    fun <T> multipartApi(
        @Path("url") url: String,
        @Part files: List<MultipartBody.Part>,
        @PartMap bodyMap: HashMap<String, RequestBody>
    ): Call<T>

}