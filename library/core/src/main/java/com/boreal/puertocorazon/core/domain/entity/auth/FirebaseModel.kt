package com.boreal.puertocorazon.core.domain.entity.auth

import android.os.Parcelable
import io.realm.RealmObject
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
open class FirebaseModel(
    var identities: @RawValue Identities? = Identities(),
    var sign_in_provider: String = ""
) : Parcelable, RealmObject()