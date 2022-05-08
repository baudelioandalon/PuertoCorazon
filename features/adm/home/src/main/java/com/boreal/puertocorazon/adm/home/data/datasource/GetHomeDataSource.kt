package com.boreal.puertocorazon.adm.home.data.datasource

import com.boreal.puertocorazon.core.domain.entity.AFirestoreGetResponse
import com.boreal.puertocorazon.core.domain.entity.event.PCEventModel
import kotlinx.coroutines.flow.Flow

interface GetHomeDataSource {
    suspend fun executeGetEvents(
        idKey: String = "",
        collectionPath: String = "",
    ): Flow<AFirestoreGetResponse<List<PCEventModel>>>
}