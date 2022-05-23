package com.boreal.puertocorazon.core.domain

import com.boreal.puertocorazon.core.domain.entity.AFirestoreGetResponse
import com.boreal.puertocorazon.core.domain.entity.event.PCEventModel
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    suspend fun executeGetEvent(
        idEvent: String = "",
        collectionPath: String = ""
    ): Flow<AFirestoreGetResponse<PCEventModel>>
}