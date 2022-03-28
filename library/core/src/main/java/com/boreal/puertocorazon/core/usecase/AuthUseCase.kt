package com.boreal.puertocorazon.core.usecase

import com.boreal.puertocorazon.core.domain.AuthRepository
import com.boreal.puertocorazon.core.domain.entity.AFirestoreStatusRequest
import com.boreal.puertocorazon.core.domain.entity.auth.AAuthModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class AuthUseCase(private val authRepository: AuthRepository) :
    UseCase<EmptyIn, AuthUseCase.Output> {

    inner class Output(val response: Pair<AFirestoreStatusRequest, AAuthModel?>) : Out()

    override suspend fun execute(input: EmptyIn): Flow<Output> {
        return authRepository.executeAuthUser(input).flowOn(Dispatchers.IO).map {
            Output(it)
        }
    }

}