package com.boreal.altemis.core.domain.entity.auth

import com.boreal.puertocorazon.core.domain.entity.auth.AAuthModel
import com.google.gson.Gson
import kotlin.reflect.KClass

class AAuthConvert<T>(private val vklass: KClass<*>) {

    fun getDataType(document: Map<String,Any>) =
        with(Gson()) {
            fromJson<AAuthModel>(toJson(document as Map<*, *>).toString(), vklass.javaObjectType) as T
        }

}