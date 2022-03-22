package com.boreal.puertocorazon.login.data.datasource.remote

import com.boreal.puertocorazon.core.domain.entity.AFirestoreAuthResponse
import com.boreal.puertocorazon.core.domain.entity.auth.AAuthLoginEmailModel
import com.boreal.puertocorazon.core.domain.entity.auth.AAuthModel
import com.boreal.puertocorazon.core.utils.corefirestore.errorhandler.CUAuthenticationErrorEnum
import com.boreal.puertocorazon.login.data.LoginDataSource
import com.boreal.puertocorazon.login.data.datasource.GetLoginDataSource

class ARemoteLoginDataSource : GetLoginDataSource {

    override suspend fun executeLogin(request: AAuthLoginEmailModel):
            AFirestoreAuthResponse<AAuthLoginEmailModel, AAuthModel, CUAuthenticationErrorEnum> =
        LoginDataSource.getLogin(request)
}
