package com.boreal.puertocorazon.core.data.datasource.remote

import com.boreal.puertocorazon.core.repository.home.HomeDataSource
import com.boreal.puertocorazon.core.data.datasource.GetHomeDataSource
import com.boreal.puertocorazon.core.domain.entity.AFirestoreGetResponse
import com.boreal.puertocorazon.core.domain.entity.event.PCEventModel
import kotlinx.coroutines.flow.Flow

class PCRemoteHomeDataSource : GetHomeDataSource {

    override suspend fun executeGetEvents(
        idKey: String,
        collectionPath: String
    ): Flow<AFirestoreGetResponse<List<PCEventModel>>> =
        HomeDataSource.getEvents(idKey, collectionPath)

}
