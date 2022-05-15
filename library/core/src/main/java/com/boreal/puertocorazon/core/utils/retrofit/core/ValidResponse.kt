package com.boreal.puertocorazon.core.utils.retrofit.core

import com.google.gson.Gson
import okhttp3.ResponseBody
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
        if (requestExecuted.isSuccessful) {
            val jsonObjectArray = gson.toJsonTree(requestExecuted.body()).asJsonArray
            val myResponse =
                Gson().fromJson(jsonObjectArray, vkClass.javaObjectType) as R
            DataResponse(
                statusRequest = StatusRequestEnum.SUCCESS,
                successData = myResponse
            )
        } else {
            val errorBody = requestExecuted.errorBody() as ResponseBody
            if (errorBody.string().contains("could not handle the request")) {
                DataResponse(
                    statusRequest = StatusRequestEnum.FAILURE,
                    errorData = "Hay un error en la peticion"
                )
            } else {
                val objJson = bufferedToJson(
                    errorBody.charStream().buffered()
                )
                val myResponse = Gson().fromJson(objJson.toString(), vkClass.javaObjectType) as R
                DataResponse(
                    statusRequest = StatusRequestEnum.FAILURE,
                    errorModel = myResponse
                )
            }
        }
    } catch (exception: Exception) {
        DataResponse(
            statusRequest = StatusRequestEnum.FAILURE,
            errorData = "Algo salio mal"
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