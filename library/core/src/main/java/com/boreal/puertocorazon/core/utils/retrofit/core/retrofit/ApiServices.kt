package com.boreal.puertocorazon.core.utils.retrofit.core.retrofit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface ApiServices {

    @GET
    fun serviceResponseGetNoHeaderNoBody(@Url url: String): Call<Any>

    @POST
    fun serviceResponsePostBody(@Url url: String, @Body requestBody: Any): Call<Any>

}