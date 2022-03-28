package com.boreal.puertocorazon.adm.home.data.datasource

import com.boreal.puertocorazon.core.domain.entity.AFirestoreGetResponse
import com.boreal.puertocorazon.core.domain.entity.event.PCEventModel

interface GetHomeDataSource {
    suspend fun executeGetEvents(
        idKey: String = "",
        collectionPath: String = "",
    ): AFirestoreGetResponse<List<PCEventModel>>
}