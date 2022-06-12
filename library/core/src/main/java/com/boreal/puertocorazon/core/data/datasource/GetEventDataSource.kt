package com.boreal.puertocorazon.core.data.datasource

import com.boreal.puertocorazon.core.domain.entity.AFirestoreGetResponse
import com.boreal.puertocorazon.core.domain.entity.event.PCEventModel
import kotlinx.coroutines.flow.Flow

interface GetEventDataSource {
    suspend fun executeGetEvent(
        idEvent: String = "",
        collectionPath: String = ""
    ): AFirestoreGetResponse<PCEventModel>

    suspend fun executeGetRealTimeEvent(
        idEvent: String = "",
        collectionPath: String = ""
    ): Flow<AFirestoreGetResponse<PCEventModel>>
}