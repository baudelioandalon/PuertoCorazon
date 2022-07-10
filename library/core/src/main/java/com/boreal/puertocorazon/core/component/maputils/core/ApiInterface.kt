package com.boreal.puertocorazon.core.component.maputils.core

import com.boreal.puertocorazon.core.component.maputils.model.ModelPlaces
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("place/queryautocomplete/json")
    suspend fun getPlace(
        @Query("input") text: String,
        @Query("key") key: String
    ): ModelPlaces
}