package com.boreal.puertocorazon.addevent.data.datasource

import com.boreal.puertocorazon.core.domain.entity.AFirestoreSetResponse
import com.boreal.puertocorazon.core.domain.entity.auth.AAuthLoginEmailModel
import com.boreal.puertocorazon.core.domain.entity.event.PCEventModel
import com.boreal.puertocorazon.core.domain.entity.event.PCEventToUploadModel
import com.boreal.puertocorazon.core.utils.corefirestore.errorhandler.CUFirestoreErrorEnum

interface SetEventDataSource {
    suspend fun executeAddEvent(
        request: PCEventModel
    ): AFirestoreSetResponse<PCEventModel, CUFirestoreErrorEnum>
}