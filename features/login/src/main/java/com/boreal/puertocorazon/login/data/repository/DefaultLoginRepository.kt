package com.boreal.puertocorazon.login.data.repository

import com.boreal.puertocorazon.core.domain.AFirestoreAuthResponse
import com.boreal.puertocorazon.core.domain.entity.auth.AAuthLoginEmailModel
import com.boreal.puertocorazon.core.utils.corefirestore.errorhandler.CUAuthenticationErrorEnum
import com.boreal.puertocorazon.login.ui.login.data.datasource.GetLoginDataSource
import com.boreal.puertocorazon.login.ui.login.domain.LoginRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DefaultLoginRepository(private val getLoginDataSource: GetLoginDataSource) : LoginRepository {

    override fun executeLogin(request: AAuthLoginEmailModel): Flow<AFirestoreAuthResponse<AAuthLoginEmailModel, Task<AuthResult>, CUAuthenticationErrorEnum>> =
        flow {
            getLoginDataSource.executeLogin(request)
        }
}