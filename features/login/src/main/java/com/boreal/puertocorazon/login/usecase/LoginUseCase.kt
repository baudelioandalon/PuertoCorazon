package com.boreal.puertocorazon.login.usecase

import com.boreal.puertocorazon.core.domain.AFirestoreAuthResponse
import com.boreal.puertocorazon.core.domain.entity.auth.AAuthLoginEmailModel
import com.boreal.puertocorazon.core.usecase.In
import com.boreal.puertocorazon.core.usecase.Out
import com.boreal.puertocorazon.core.usecase.UseCase
import com.boreal.puertocorazon.core.utils.corefirestore.errorhandler.CUAuthenticationErrorEnum
import com.boreal.puertocorazon.login.domain.LoginRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class LoginUseCase(private val loginRepository: LoginRepository) :
    UseCase<LoginUseCase.Input, LoginUseCase.Output> {

    class Input(val request: AAuthLoginEmailModel) : In()
    inner class Output(val response: AFirestoreAuthResponse<AAuthLoginEmailModel, Task<AuthResult>, CUAuthenticationErrorEnum>) :
        Out()

    override suspend fun execute(input: Input): Flow<Output> {
        return loginRepository.executeLogin(input.request).flowOn(Dispatchers.IO).map {
            Output(it)
        }
    }

}