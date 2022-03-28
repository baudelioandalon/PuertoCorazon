package com.boreal.puertocorazon.adm.home.usecase

import com.boreal.puertocorazon.adm.home.domain.HomeRepository
import com.boreal.puertocorazon.core.domain.entity.AFirestoreGetResponse
import com.boreal.puertocorazon.core.domain.entity.event.PCEventModel
import com.boreal.puertocorazon.core.usecase.In
import com.boreal.puertocorazon.core.usecase.Out
import com.boreal.puertocorazon.core.usecase.UseCase
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