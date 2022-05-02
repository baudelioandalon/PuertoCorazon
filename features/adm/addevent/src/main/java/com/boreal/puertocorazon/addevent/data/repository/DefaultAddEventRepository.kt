package com.boreal.puertocorazon.addevent.data.repository

import com.boreal.puertocorazon.addevent.data.datasource.AddEventDataSource
import com.boreal.puertocorazon.addevent.domain.AddEventRepository
import com.boreal.puertocorazon.core.domain.entity.AFirestoreSetResponse
import com.boreal.puertocorazon.core.domain.entity.event.PCEventToUploadModel
import com.boreal.puertocorazon.core.utils.corefirestore.errorhandler.CUFirestoreErrorEnum
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DefaultAddEventRepository(
    private val addEventDataSource: AddEventDataSource
) : AddEventRepository {
    override suspend fun executeAddEvent(request: PCEventToUploadModel): Flow<AFirestoreSetResponse<PCEventToUploadModel, CUFirestoreErrorEnum>> =
        flow {
            emit(addEventDataSource.executeAddEvent(request))
        }
}