package com.boreal.puertocorazon.core.data.datasource.remote

import com.boreal.puertocorazon.core.data.datasource.GetEventDataSource
import com.boreal.puertocorazon.core.domain.entity.AFirestoreGetResponse
import com.boreal.puertocorazon.core.domain.entity.event.PCEventModel
import com.boreal.puertocorazon.core.repository.event.EventDataSource
import kotlinx.coroutines.flow.Flow

class PCRemoteEventDataSource : GetEventDataSource {

    override suspend fun executeGetEvent(
        idEvent: String,
        collectionPath: String
    ): AFirestoreGetResponse<PCEventModel> =
        EventDataSource.getEvent(idEvent, collectionPath)

    override suspend fun executeGetRealTimeEvent(
        idEvent: String,
        collectionPath: String
    ): Flow<AFirestoreGetResponse<PCEventModel>> =
        EventDataSource.getEventRealTime(idEvent, collectionPath)
}
