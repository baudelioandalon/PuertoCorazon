package com.boreal.puertocorazon.core.utils.realm

import io.realm.Realm
import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.kotlin.executeTransactionAwait
import io.realm.kotlin.where
import kotlinx.coroutines.Dispatchers

suspend fun RealmObject.saveLocal() {
    Realm.getDefaultInstance().executeTransactionAwait(Dispatchers.IO) {
        it.insertOrUpdate(this)
    }
}

inline fun <reified T : RealmObject> autoGenerateId(primaryKey: String = "id"): Int {
    val currentIdNumber = Realm.getDefaultInstance().where(T::class.java).max(primaryKey)
    return if (currentIdNumber == null) {
        0
    } else {
        currentIdNumber.toInt() + 1
    }
}

inline fun <reified T : RealmObject> getRealmObject(): T? =
    with(Realm.getDefaultInstance()) { where(T::class.java).findFirst() }