package com.boreal.puertocorazon.addevent.usecase

import com.boreal.puertocorazon.addevent.domain.AddEventRepository
import com.boreal.puertocorazon.core.domain.entity.AFirestoreSetResponse
import com.boreal.puertocorazon.core.domain.entity.event.PCEventModel
import com.boreal.puertocorazon.core.usecase.In
import com.boreal.puertocorazon.core.usecase.Out
import com.boreal.puertocorazon.core.usecase.UseCase
import com.boreal.puertocorazon.core.utils.corefirestore.errorhandler.CUFirestoreErrorEnum
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class AddEventUseCase(private val addEventRepository: AddEventRepository) :
    UseCase<AddEventUseCase.Input, AddEventUseCase.Output> {

    class Input(val request: PCEventModel) : In()
    inner class Output(val response: AFirestoreSetResponse<PCEventModel, CUFirestoreErrorEnum>) :
        Out()

    override suspend fun execute(input: Input): Flow<Output> {
        return addEventRepository.executeAddEvent(input.request).flowOn(Dispatchers.IO).map {
            Output(it)
        }
    }

}