package com.boreal.puertocorazon.login.usecase

import com.boreal.puertocorazon.core.domain.entity.AFirestoreAuthResponse
import com.boreal.puertocorazon.core.domain.entity.auth.AAuthLoginEmailModel
import com.boreal.puertocorazon.core.domain.entity.auth.AAuthModel
import com.boreal.puertocorazon.core.usecase.login.In
import com.boreal.puertocorazon.core.usecase.login.Out
import com.boreal.puertocorazon.core.usecase.login.UseCase
import com.boreal.puertocorazon.core.utils.corefirestore.errorhandler.CUAuthenticationErrorEnum
import com.boreal.puertocorazon.login.domain.LoginNormalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class LoginNormalUseCase(private val loginNormalRepository: LoginNormalRepository) :
    UseCase<LoginNormalUseCase.Input, LoginNormalUseCase.Output> {

    class Input(val request: AAuthLoginEmailModel) : In()
    inner class Output(val response: AFirestoreAuthResponse<AAuthLoginEmailModel?, AAuthModel?, CUAuthenticationErrorEnum>) :
        Out()

    override suspend fun execute(input: Input): Flow<Output> {
        return loginNormalRepository.executeLogin(input.request).flowOn(Dispatchers.IO).map {
            Output(it)
        }
    }

}