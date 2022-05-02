package com.boreal.puertocorazon.addevent.data.datasource.remote

import com.boreal.puertocorazon.addevent.data.datasource.AddEventDataSource
import com.boreal.puertocorazon.core.domain.entity.AFirestoreSetResponse
import com.boreal.puertocorazon.core.domain.entity.event.PCEventToUploadModel
import com.boreal.puertocorazon.core.utils.corefirestore.errorhandler.CUFirestoreErrorEnum

class PCRemoteAddEventDataSource: AddEventDataSource {
    override suspend fun executeAddEvent(request: PCEventToUploadModel): AFirestoreSetResponse<PCEventToUploadModel, CUFirestoreErrorEnum> {
        return AFirestoreSetResponse()
    }
}