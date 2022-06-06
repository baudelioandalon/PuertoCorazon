package com.boreal.puertocorazon.adm.checking.domain.datasource.remote

import com.boreal.puertocorazon.adm.checking.domain.datasource.UpdateCheckingDataSource
import com.boreal.puertocorazon.core.domain.entity.AFirestoreSetResponse
import com.boreal.puertocorazon.core.utils.corefirestore.errorhandler.CUFirestoreErrorEnum

class PCRemoteUpdateCheckingSource : UpdateCheckingDataSource {

    override suspend fun executeUpdateChecking(
        checkingModel: ArrayList<Map<String, Any>>,
        collectionPath: String,
        documentPath: String
    ): AFirestoreSetResponse<ArrayList<Map<String, Any>>, CUFirestoreErrorEnum> =
        CheckingDataSource.updateChecking(checkingModel, collectionPath, documentPath)
}
