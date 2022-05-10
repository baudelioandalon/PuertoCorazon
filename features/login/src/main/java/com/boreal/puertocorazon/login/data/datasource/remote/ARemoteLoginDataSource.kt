package com.boreal.puertocorazon.login.data.datasource.remote

import com.boreal.puertocorazon.core.domain.entity.AFirestoreAuthResponse
import com.boreal.puertocorazon.core.domain.entity.auth.AAuthLoginEmailModel
import com.boreal.puertocorazon.core.domain.entity.auth.AAuthModel
import com.boreal.puertocorazon.core.utils.corefirestore.errorhandler.CUAuthenticationErrorEnum
import com.boreal.puertocorazon.login.data.LoginDataSource
import com.boreal.puertocorazon.login.data.datasource.GetLoginDataSource
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.identity.SignInCredential

class ARemoteLoginDataSource : GetLoginDataSource {

    override suspend fun executeNormalLogin(request: AAuthLoginEmailModel):
            AFirestoreAuthResponse<AAuthLoginEmailModel?, AAuthModel?, CUAuthenticationErrorEnum> =
        LoginDataSource.getNormalLogin(request)

    override suspend fun executeGoogleLogin(signInCredential: SignInCredential): AFirestoreAuthResponse<AAuthLoginEmailModel?, AAuthModel?, CUAuthenticationErrorEnum> =
        LoginDataSource.getGoogleLogin(signInCredential)

}
