package com.boreal.puertocorazon.core.domain.entity

import com.google.firebase.firestore.DocumentSnapshot
import kotlin.collections.ArrayList


//TODO MOVE TO UNIQUE EXTENSION'S FILE
inline fun <reified T> DocumentSnapshot.convertData(idData: String) = this[idData, T::class.java]!!

inline fun <reified T> List<DocumentSnapshot>.convertDataToList(idData: String) =
    with(ArrayList<T>()) {
        this@convertDataToList.forEach {
            add(it.convertData(idData))
        }
        this
    }