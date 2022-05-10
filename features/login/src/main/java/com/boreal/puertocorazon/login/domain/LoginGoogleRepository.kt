package com.boreal.puertocorazon.login.domain

import com.boreal.puertocorazon.core.domain.entity.AFirestoreAuthResponse
import com.boreal.puertocorazon.core.domain.entity.auth.AAuthLoginEmailModel
import com.boreal.puertocorazon.core.domain.entity.auth.AAuthModel
import com.boreal.puertocorazon.core.utils.corefirestore.errorhandler.CUAuthenticationErrorEnum
import com.google.android.gms.auth.api.identity.SignInCredential
import kotlinx.coroutines.flow.Flow

interface LoginGoogleRepository {
    suspend fun executeGoogleLogin(signInCredential: SignInCredential):
            Flow<AFirestoreAuthResponse<AAuthLoginEmailModel?, AAuthModel?, CUAuthenticationErrorEnum>>
}