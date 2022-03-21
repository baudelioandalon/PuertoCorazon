package com.boreal.puertocorazon.core.domain.entity

import com.google.firebase.firestore.DocumentSnapshot
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlin.reflect.KClass

class AFirestoreConvert<T>(val vklass: KClass<*>) {

    fun getDataType(document: DocumentSnapshot, idData: String): List<T> {
        val jsonArray = Gson().toJsonTree(document[idData], ArrayList<T>()::class.java).asJsonArray
        return ((GsonBuilder().create()
            .fromJson(jsonArray, vklass.javaObjectType)) as Array<T>).toList()
    }
}