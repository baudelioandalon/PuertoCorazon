package com.boreal.puertocorazon.login.data.repository

import com.boreal.puertocorazon.core.domain.entity.AFirestoreAuthResponse
import com.boreal.puertocorazon.core.domain.entity.auth.AAuthLoginEmailModel
import com.boreal.puertocorazon.core.domain.entity.auth.AAuthModel
import com.boreal.puertocorazon.core.utils.corefirestore.errorhandler.CUAuthenticationErrorEnum
import com.boreal.puertocorazon.login.data.datasource.GetLoginDataSource
import com.boreal.puertocorazon.login.domain.LoginRepository
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DefaultLoginRepository(private val getLoginDataSource: GetLoginDataSource) : LoginRepository {

    override suspend fun executeLogin(request: AAuthLoginEmailModel): Flow<AFirestoreAuthResponse<AAuthLoginEmailModel, AAuthModel, CUAuthenticationErrorEnum>> =
        flow {
            emit(getLoginDataSource.executeLogin(request))
        }
}