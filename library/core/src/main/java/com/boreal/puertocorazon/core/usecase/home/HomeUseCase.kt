package com.boreal.puertocorazon.core.usecase.home

import com.boreal.puertocorazon.core.domain.HomeRepository
import com.boreal.puertocorazon.core.domain.entity.AFirestoreGetResponse
import com.boreal.puertocorazon.core.domain.entity.event.PCEventModel
import com.boreal.puertocorazon.core.usecase.login.In
import com.boreal.puertocorazon.core.usecase.login.Out
import com.boreal.puertocorazon.core.usecase.login.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class HomeUseCase(private val homeRepository: HomeRepository) :
    UseCase<HomeUseCase.Input, HomeUseCase.Output> {

    class Input(
        val idKey: String,
        val collectionPath: String
    ) : In()

    inner class Output(val response: AFirestoreGetResponse<List<PCEventModel>>) :
        Out()

    override suspend fun execute(input: Input): Flow<Output> {
        return homeRepository.executeGetEvents(input.idKey, input.collectionPath)
            .flowOn(Dispatchers.IO).map {
                Output(it)
            }
    }

}