package com.boreal.puertocorazon.core.utils.retrofit.core

import com.boreal.puertocorazon.core.domain.entity.payment.PCPaymentErrorModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import retrofit2.Call
import java.io.BufferedReader
import kotlin.reflect.KClass

class ValidResponse<R>(
    private val vkClass: KClass<*>
) {
    fun validationMethod(result: Call<R>): DataResponse<R> = try {
        val requestExecuted = result.execute()
        val gson = Gson()
        if (requestExecuted.isSuccessful && requestExecuted.code() == 200) {
            val jsonObject = gson.toJsonTree(requestExecuted.body())
            val myResponse =
                Gson().fromJson(jsonObject, vkClass.javaObjectType) as R
            DataResponse(
                statusRequest = StatusRequestEnum.SUCCESS,
                successData = myResponse
            )
        } else {
            if (requestExecuted.errorBody() == null) {
                DataResponse(
                    statusRequest = StatusRequestEnum.FAILURE,
                    errorData = "Hay una intermitencia en la red de pagos, por favor,\n " +
                            "intente mas tarde, si el problema persiste,\n " +
                            "envie un correo al administrador."
                )
            } else {
                val type = object : TypeToken<PCPaymentErrorModel>() {}.type
                val errorResult: PCPaymentErrorModel? =
                    gson.fromJson(requestExecuted.errorBody()!!.charStream(), type)
                DataResponse(
                    statusRequest = StatusRequestEnum.FAILURE,
                    errorModel = errorResult
                )
            }

        }
    } catch (exception: Exception) {
        DataResponse(
            statusRequest = StatusRequestEnum.FAILURE,
            errorData = "Hay una intermitencia en la red de pagos, por favor,\n " +
                    "intente mas tarde, si el problema persiste,\n " +
                    "envie un correo al administrador."
        )
    }

    private fun bufferedToJson(bufferedReader: BufferedReader): JSONObject {
        // ... retrieve BufferedReader<br />
        val sb = StringBuilder()
        var line: String?
        while (bufferedReader.readLine().also { line = it } != null) {
            sb.append(line)
        }
        return JSONObject(sb.toString())
    }
}