package com.boreal.puertocorazon.core.utils.retrofit.core.retrofit

import retrofit2.Call
import retrofit2.http.*

interface ApiServices {

    @GET
    fun serviceResponseGetNoHeaderNoBody(@Url url: String): Call<Any>

    @POST
    fun serviceResponsePostBody(
        @Url url: String, @Body requestBody: Any,
        @HeaderMap map: HashMap<String, String> = hashMapOf(
            Pair(
                "Content-Type",
                "application/json"
            )
        )
    ): Call<Any>

}