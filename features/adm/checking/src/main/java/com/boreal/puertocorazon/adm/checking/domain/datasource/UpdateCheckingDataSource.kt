package com.boreal.puertocorazon.adm.checking.domain.datasource

import com.boreal.puertocorazon.core.domain.entity.AFirestoreSetResponse
import com.boreal.puertocorazon.core.utils.corefirestore.errorhandler.CUFirestoreErrorEnum

interface UpdateCheckingDataSource {
    suspend fun executeUpdateChecking(
        checkingModel: ArrayList<Map<String, Any>>,
        collectionPath: String,
        documentPath: String
    ): AFirestoreSetResponse<ArrayList<Map<String, Any>>, CUFirestoreErrorEnum>
}