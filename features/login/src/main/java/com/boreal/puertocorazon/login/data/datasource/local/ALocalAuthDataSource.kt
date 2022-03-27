package com.boreal.puertocorazon.login.data.datasource.local

import com.boreal.puertocorazon.core.domain.entity.AFirestoreStatusRequest
import com.boreal.puertocorazon.core.domain.entity.auth.AAuthModel
import com.boreal.puertocorazon.core.usecase.EmptyIn
import com.boreal.puertocorazon.core.utils.realm.getRealmObject
import com.boreal.puertocorazon.login.data.datasource.GetAuthUserDataSource
import com.boreal.puertocorazon.login.extension.toModel

class ALocalAuthDataSource : GetAuthUserDataSource {

    override suspend fun executeAuthUser(request: EmptyIn): Pair<AFirestoreStatusRequest, AAuthModel?> {
        val result = getRealmObject<AAuthModel>().toString().toModel<AAuthModel>()
        return if (result != null) Pair(AFirestoreStatusRequest.SUCCESS, result) else Pair(
            AFirestoreStatusRequest.FAILURE,
            result
        )
    }
}