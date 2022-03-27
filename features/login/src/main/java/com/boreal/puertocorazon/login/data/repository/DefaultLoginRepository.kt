package com.boreal.puertocorazon.login.data.repository

import com.boreal.puertocorazon.core.domain.entity.AFirestoreAuthResponse
import com.boreal.puertocorazon.core.domain.entity.auth.AAuthLoginEmailModel
import com.boreal.puertocorazon.core.domain.entity.auth.AAuthModel
import com.boreal.puertocorazon.core.utils.corefirestore.errorhandler.CUAuthenticationErrorEnum
import com.boreal.puertocorazon.core.utils.realm.saveLocal
import com.boreal.puertocorazon.login.data.datasource.GetLoginDataSource
import com.boreal.puertocorazon.login.domain.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DefaultLoginRepository(private val getLoginDataSource: GetLoginDataSource) : LoginRepository {

    override suspend fun executeLogin(request: AAuthLoginEmailModel): Flow<AFirestoreAuthResponse<AAuthLoginEmailModel, AAuthModel, CUAuthenticationErrorEnum>> =
        flow {
            val result = getLoginDataSource.executeLogin(request)
            if (result.response != null && result.response?.userType != "NONE" && !result.response?.userType.isNullOrEmpty()) {
                result.response?.let {
                    it.saveLocal()
                }
            }
            emit(result)
        }
}