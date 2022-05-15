package com.boreal.puertocorazon.core.utils.retrofit.core

import com.google.gson.Gson
import retrofit2.Call
import kotlin.reflect.KClass

class ValidResponse<R>(
    private val vkClass: KClass<*>
) {
    fun validationMethod(result: Call<R>): DataResponse<R> {
        val requestExecuted = result.execute()
        return if (requestExecuted.isSuccessful) {
            val gson = Gson()
            val jsonObjectArray = gson.toJsonTree(requestExecuted.body()).asJsonArray
            val myResponse =
                Gson().fromJson(jsonObjectArray, vkClass.javaObjectType) as R
            DataResponse(
                statusRequest = StatusRequestEnum.SUCCESS,
                successData = myResponse
            )
        } else {
            DataResponse(
                statusRequest = StatusRequestEnum.FAILURE,
                errorData = requestExecuted.message() ?: "Algo salio mal"
            )
        }
    }
}