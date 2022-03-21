package com.boreal.puertocorazon.core.utils.realm

import io.realm.Realm
import io.realm.RealmModel
import io.realm.RealmObject
import io.realm.RealmResults

fun RealmObject.saveLocal() {
    Realm.Transaction { realmInstance ->
        realmInstance.insertOrUpdate(this)
    }
}

fun RealmModel.saveLocal() {
    Realm.Transaction { realmInstance ->
        realmInstance.insertOrUpdate(this)
    }
}

fun RealmObject.autoGenerateId(primaryKey: String = "id"): Int {
    val currentIdNumber = realm.where(this::class.java).max(primaryKey)
    return if (currentIdNumber == null) {
        1
    } else {
        currentIdNumber.toInt() + 1
    }
}

inline fun <reified T : RealmObject> getRealmObject(): RealmResults<T> =
    with(Realm.getDefaultInstance()) { where(T::class.java).findAll() }


inline fun <reified T : RealmModel> getRealmModel(): RealmResults<T> =
    with(Realm.getDefaultInstance()) { where(T::class.java).findAll() }