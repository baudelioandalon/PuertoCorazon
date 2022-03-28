package com.boreal.puertocorazon.adm.home.data.datasource.remote

import com.boreal.puertocorazon.adm.home.data.HomeDataSource
import com.boreal.puertocorazon.adm.home.data.datasource.GetHomeDataSource
import com.boreal.puertocorazon.core.domain.entity.AFirestoreGetResponse
import com.boreal.puertocorazon.core.domain.entity.event.PCEventModel

class PCRemoteHomeDataSource : GetHomeDataSource {

    override suspend fun executeGetEvents(
        idKey: String,
        collectionPath: String
    ): AFirestoreGetResponse<List<PCEventModel>> =
        HomeDataSource.getEvents(idKey, collectionPath)

}
