package com.boreal.puertocorazon.addevent.data.repository

import com.boreal.puertocorazon.addevent.data.datasource.SetEventDataSource
import com.boreal.puertocorazon.addevent.domain.AddEventRepository
import com.boreal.puertocorazon.core.domain.entity.AFirestoreSetResponse
import com.boreal.puertocorazon.core.domain.entity.event.PCEventModel
import com.boreal.puertocorazon.core.domain.entity.event.PCEventToUploadModel
import com.boreal.puertocorazon.core.utils.corefirestore.errorhandler.CUFirestoreErrorEnum
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DefaultAddEventRepository(
    private val setEventDataSource: SetEventDataSource
) : AddEventRepository {
    override suspend fun executeAddEvent(request: PCEventModel): Flow<AFirestoreSetResponse<PCEventModel, CUFirestoreErrorEnum>> =
        flow {
            emit(setEventDataSource.executeAddEvent(request))
        }
}