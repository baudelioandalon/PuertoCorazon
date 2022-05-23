package com.boreal.puertocorazon.core.repository.event

import com.boreal.puertocorazon.core.data.datasource.GetEventDataSource
import com.boreal.puertocorazon.core.domain.EventRepository
import com.boreal.puertocorazon.core.domain.entity.AFirestoreGetResponse
import com.boreal.puertocorazon.core.domain.entity.event.PCEventModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DefaultEventRepository(
    private val getEventDataSource: GetEventDataSource
) : EventRepository {
    override suspend fun executeGetEvent(
        idEvent: String,
        collectionPath: String
    ): Flow<AFirestoreGetResponse<PCEventModel>> = flow {
        emit(getEventDataSource.executeGetEvent(idEvent, collectionPath))
    }

}