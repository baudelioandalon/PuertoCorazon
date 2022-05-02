package com.boreal.puertocorazon.addevent.data.datasource

import com.boreal.puertocorazon.core.domain.entity.AFirestoreSetResponse
import com.boreal.puertocorazon.core.domain.entity.auth.AAuthLoginEmailModel
import com.boreal.puertocorazon.core.domain.entity.event.PCEventToUploadModel
import com.boreal.puertocorazon.core.utils.corefirestore.errorhandler.CUFirestoreErrorEnum

interface AddEventDataSource {
    suspend fun executeAddEvent(
        request: PCEventToUploadModel
    ): AFirestoreSetResponse<PCEventToUploadModel, CUFirestoreErrorEnum>
}