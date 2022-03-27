package com.boreal.puertocorazon.core.domain.entity.auth

import io.realm.RealmList
import io.realm.RealmObject

open class Identities(
    var email: RealmList<String> = RealmList()
) : RealmObject()