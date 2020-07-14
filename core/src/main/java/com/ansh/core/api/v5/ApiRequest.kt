package com.ansh.core.api.v5

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiRequest {

    @GET("{url}")
    fun <B, R> getApi(
        @Path("url") url: String,
        @Body request: B
    ): Call<R>

    @POST("{url}")
    fun <B, R> postApi(
        @Path("url") url: String,
        @Body request: B
    ): Call<R>


//    @GET("{url}")
//    fun getApi(
//        @Path("url") url: String,
//        @HeaderMap headers: Headers
//    ): Call<JsonObject>
//
//    @POST("{url}")
//    fun postApi(
//        @Path("url") url: String,
//        @Body jsonObject: JsonObject
//    ): Call<JsonObject>
//
//    @FormUrlEncoded
//    @POST("{url}")
//    fun postApi(
//        @Path("url") url: String,
//        @FieldMap bodyMap: HashMap<String, String>
//    ): Call<JsonObject>
//
//    @Multipart
//    @POST("{url}")
//    fun multipartApi(
//        @Path("url") url: String,
//        @Part files: List<MultipartBody.Part>,
//        @PartMap bodyMap: HashMap<String, RequestBody>
//    ): Call<JsonObject>

}