package com.boreal.puertocorazon.core.domain

import com.boreal.puertocorazon.core.domain.entity.AFirestoreStatusRequest
import com.boreal.puertocorazon.core.domain.entity.auth.AAuthModel
import com.boreal.puertocorazon.core.usecase.login.EmptyIn
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun executeAuthUser(request: EmptyIn):
            Flow<Pair<AFirestoreStatusRequest, AAuthModel?>>
}