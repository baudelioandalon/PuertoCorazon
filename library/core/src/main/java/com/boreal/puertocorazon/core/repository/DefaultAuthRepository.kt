package com.boreal.puertocorazon.core.repository

import com.boreal.puertocorazon.core.domain.entity.AFirestoreStatusRequest
import com.boreal.puertocorazon.core.domain.entity.auth.AAuthModel
import com.boreal.puertocorazon.core.usecase.EmptyIn
import com.boreal.puertocorazon.core.data.datasource.GetAuthUserDataSource
import com.boreal.puertocorazon.core.domain.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DefaultAuthRepository(
    private val getAuthUserDataSource: GetAuthUserDataSource
) : AuthRepository {

    override suspend fun executeAuthUser(request: EmptyIn): Flow<Pair<AFirestoreStatusRequest,AAuthModel?>> =
        flow {
            emit(getAuthUserDataSource.executeAuthUser(request))
        }

}