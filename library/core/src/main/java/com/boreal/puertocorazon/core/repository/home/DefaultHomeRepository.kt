package com.boreal.puertocorazon.core.repository.home

import com.boreal.puertocorazon.core.data.datasource.GetHomeDataSource
import com.boreal.puertocorazon.core.domain.HomeRepository
import com.boreal.puertocorazon.core.domain.entity.AFirestoreGetResponse
import com.boreal.puertocorazon.core.domain.entity.event.PCEventModel
import kotlinx.coroutines.flow.Flow

class DefaultHomeRepository(
    private val getHomeDataSource: GetHomeDataSource
) : HomeRepository {

    override suspend fun executeGetEvents(
        idKey: String,
        collectionPath: String
    ): Flow<AFirestoreGetResponse<List<PCEventModel>>> =
        getHomeDataSource.executeGetEvents(idKey, collectionPath)
}