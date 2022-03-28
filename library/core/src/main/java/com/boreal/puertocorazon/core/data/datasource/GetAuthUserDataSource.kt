package com.boreal.puertocorazon.core.data.datasource

import com.boreal.puertocorazon.core.domain.entity.AFirestoreStatusRequest
import com.boreal.puertocorazon.core.domain.entity.auth.AAuthModel
import com.boreal.puertocorazon.core.usecase.EmptyIn

interface GetAuthUserDataSource {
    suspend fun executeAuthUser(
        request: EmptyIn
    ): Pair<AFirestoreStatusRequest,AAuthModel?>
}