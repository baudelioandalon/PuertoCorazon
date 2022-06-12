package com.boreal.puertocorazon.core.usecase.event

import com.boreal.puertocorazon.core.domain.EventRepository
import com.boreal.puertocorazon.core.domain.entity.AFirestoreGetResponse
import com.boreal.puertocorazon.core.domain.entity.event.PCEventModel
import com.boreal.puertocorazon.core.usecase.login.In
import com.boreal.puertocorazon.core.usecase.login.Out
import com.boreal.puertocorazon.core.usecase.login.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class EventUseCase(private val eventRepository: EventRepository) :
    UseCase<EventUseCase.Input, EventUseCase.Output> {

    class Input(
        val idEvent: String,
        val collectionPath: String,
        val realtime: Boolean
    ) : In()

    inner class Output(val response: AFirestoreGetResponse<PCEventModel>) :
        Out()

    override suspend fun execute(input: Input): Flow<Output> {
        return eventRepository.executeGetEvent(input.idEvent, input.collectionPath, input.realtime)
            .flowOn(Dispatchers.IO).map {
                Output(it)
            }
    }

}