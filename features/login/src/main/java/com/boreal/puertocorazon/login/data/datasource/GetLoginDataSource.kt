package com.boreal.puertocorazon.login.data.datasource

import com.boreal.puertocorazon.core.domain.entity.AFirestoreAuthResponse
import com.boreal.puertocorazon.core.domain.entity.auth.AAuthLoginEmailModel
import com.boreal.puertocorazon.core.utils.corefirestore.errorhandler.CUAuthenticationErrorEnum
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface GetLoginDataSource {
    suspend fun executeLogin(
        request: AAuthLoginEmailModel
    ): AFirestoreAuthResponse<AAuthLoginEmailModel, AuthResult, CUAuthenticationErrorEnum>
}