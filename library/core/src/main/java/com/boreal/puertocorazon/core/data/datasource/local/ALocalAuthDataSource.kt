package com.boreal.puertocorazon.core.data.datasource.local

import com.boreal.puertocorazon.core.data.datasource.GetAuthUserDataSource
import com.boreal.puertocorazon.core.domain.entity.AFirestoreStatusRequest
import com.boreal.puertocorazon.core.domain.entity.auth.AAuthModel
import com.boreal.puertocorazon.core.extension.toModel
import com.boreal.puertocorazon.core.usecase.EmptyIn
import com.boreal.puertocorazon.core.utils.realm.getRealmObject

class ALocalAuthDataSource : GetAuthUserDataSource {

    override suspend fun executeAuthUser(request: EmptyIn): Pair<AFirestoreStatusRequest, AAuthModel?> {
        return with(getRealmObject<AAuthModel>().toString().toModel<AAuthModel>()) {
            try {
                if (this != null) Pair(AFirestoreStatusRequest.SUCCESS, this) else Pair(
                    AFirestoreStatusRequest.FAILURE,
                    null
                )
            } catch (e: Exception) {
                Pair(
                    AFirestoreStatusRequest.FAILURE,
                    null
                )
            }
        }
    }
}