package com.boreal.puertocorazon.core.domain.entity.auth

import com.boreal.puertocorazon.core.utils.realm.autoGenerateId
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class AAuthModel(
    @PrimaryKey
    var id: Int? = null,
    var aud: String = "",
    var auth_time: Int = 0,
    var dateCreated: Long = 0L,
    var email: String = "",
    var email_verified: Boolean = false,
    var exp: Int = 0,
    var firebase: FirebaseModel? = FirebaseModel(),
    var iat: Int = 0,
    var iss: String = "",
    var sub: String = "",
    var userType: String = "",
    var user_id: String = ""
) : RealmObject() {
    init {
        if(id == null) {
            id = autoGenerateId<AAuthModel>()
        }
    }
}