package com.boreal.puertocorazon.login.data.repository

import com.boreal.puertocorazon.core.domain.entity.AFirestoreAuthResponse
import com.boreal.puertocorazon.core.domain.entity.auth.AAuthLoginEmailModel
import com.boreal.puertocorazon.core.domain.entity.auth.AAuthModel
import com.boreal.puertocorazon.core.utils.corefirestore.errorhandler.CUAuthenticationErrorEnum
import com.boreal.puertocorazon.login.data.datasource.GetLoginDataSource
import com.boreal.puertocorazon.login.domain.LoginGoogleRepository
import com.google.android.gms.auth.api.identity.SignInCredential
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DefaultLoginGoogleRepository(
    private val getLoginDataSource: GetLoginDataSource
): LoginGoogleRepository {

    override suspend fun executeGoogleLogin(signInCredential: SignInCredential): Flow<AFirestoreAuthResponse<AAuthLoginEmailModel?, AAuthModel?, CUAuthenticationErrorEnum>> =
        flow {
            emit(getLoginDataSource.executeGoogleLogin(signInCredential))
        }
}