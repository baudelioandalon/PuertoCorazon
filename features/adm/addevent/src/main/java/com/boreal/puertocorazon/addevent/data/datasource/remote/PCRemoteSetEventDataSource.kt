package com.boreal.puertocorazon.addevent.data.datasource.remote

import com.boreal.puertocorazon.addevent.data.AddEventRemoteDataSource
import com.boreal.puertocorazon.addevent.data.datasource.SetEventDataSource
import com.boreal.puertocorazon.core.domain.entity.AFirestoreSetResponse
import com.boreal.puertocorazon.core.domain.entity.event.PCEventModel
import com.boreal.puertocorazon.core.domain.entity.event.PCEventToUploadModel
import com.boreal.puertocorazon.core.utils.corefirestore.errorhandler.CUFirestoreErrorEnum

class PCRemoteSetEventDataSource: SetEventDataSource {
    override suspend fun executeAddEvent(request: PCEventModel): AFirestoreSetResponse<PCEventModel, CUFirestoreErrorEnum> {
        return AddEventRemoteDataSource.setAddEvent(request)
    }
}