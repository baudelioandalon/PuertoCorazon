package com.boreal.puertocorazon.login.data.datasource.remote

import com.boreal.puertocorazon.core.domain.entity.auth.AAuthLoginEmailModel
import com.boreal.puertocorazon.login.data.LoginDataSource
import com.boreal.puertocorazon.login.data.datasource.GetLoginDataSource

class ARemoteLoginDataSource : GetLoginDataSource {
    override suspend fun executeLogin(request: AAuthLoginEmailModel) =
        LoginDataSource.getLogin(request)
}
