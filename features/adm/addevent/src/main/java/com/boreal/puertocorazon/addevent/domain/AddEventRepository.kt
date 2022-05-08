package com.boreal.puertocorazon.addevent.domain

import com.boreal.puertocorazon.core.domain.entity.AFirestoreSetResponse
import com.boreal.puertocorazon.core.domain.entity.event.PCEventModel
import com.boreal.puertocorazon.core.domain.entity.event.PCEventToUploadModel
import com.boreal.puertocorazon.core.utils.corefirestore.errorhandler.CUFirestoreErrorEnum
import kotlinx.coroutines.flow.Flow

interface AddEventRepository {
    suspend fun executeAddEvent(request: PCEventModel):
            Flow<AFirestoreSetResponse<PCEventModel, CUFirestoreErrorEnum>>
}