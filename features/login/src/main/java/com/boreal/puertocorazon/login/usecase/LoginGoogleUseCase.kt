package com.boreal.puertocorazon.login.usecase

import com.boreal.puertocorazon.core.domain.entity.AFirestoreAuthResponse
import com.boreal.puertocorazon.core.domain.entity.auth.AAuthLoginEmailModel
import com.boreal.puertocorazon.core.domain.entity.auth.AAuthModel
import com.boreal.puertocorazon.core.usecase.login.In
import com.boreal.puertocorazon.core.usecase.login.Out
import com.boreal.puertocorazon.core.usecase.login.UseCase
import com.boreal.puertocorazon.core.utils.corefirestore.errorhandler.CUAuthenticationErrorEnum
import com.boreal.puertocorazon.login.domain.LoginGoogleRepository
import com.google.android.gms.auth.api.identity.SignInCredential
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class LoginGoogleUseCase(private val loginGoogleRepository: LoginGoogleRepository) :
    UseCase<LoginGoogleUseCase.Input, LoginGoogleUseCase.Output> {

    class Input(val request: SignInCredential) : In()
    inner class Output(val response: AFirestoreAuthResponse<AAuthLoginEmailModel?, AAuthModel?, CUAuthenticationErrorEnum>) :
        Out()

    override suspend fun execute(input: Input): Flow<Output> {
        return loginGoogleRepository.executeGoogleLogin(input.request).flowOn(Dispatchers.IO).map {
            Output(it)
        }
    }

}