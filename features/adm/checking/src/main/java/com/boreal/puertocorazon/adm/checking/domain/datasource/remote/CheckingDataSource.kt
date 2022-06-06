package com.boreal.puertocorazon.adm.checking.domain.datasource.remote

import com.boreal.puertocorazon.core.domain.entity.AFirestoreRepository
import com.boreal.puertocorazon.core.domain.entity.AFirestoreSetResponse
import com.boreal.puertocorazon.core.domain.entity.AFirestoreStatusRequest
import com.boreal.puertocorazon.core.utils.coreauthentication.awaitTask
import com.boreal.puertocorazon.core.utils.corefirestore.errorhandler.CUFirestoreErrorEnum
import com.boreal.puertocorazon.core.utils.log
import com.google.firebase.firestore.FirebaseFirestoreException

class CheckingDataSource {
    companion object : AFirestoreRepository() {
        suspend fun updateChecking(
            checkingModel: ArrayList<Map<String, Any>>,
            collectionPath: String,
            documentPath: String
        ): AFirestoreSetResponse<ArrayList<Map<String, Any>>, CUFirestoreErrorEnum> =
            try {
                val result = firestoreInstance.collection(collectionPath)
                    .document(documentPath)
                    .update(
                        "attendedTime", checkingModel,
                    ).awaitTask()
                if (result.isSuccessful) {
                    AFirestoreSetResponse(
                        idField = "",
                        collectionPath = collectionPath,
                        documentPath = documentPath,
                        modelToSet = checkingModel,
                        status = AFirestoreStatusRequest.SUCCESS
                    )
                } else {
                    AFirestoreSetResponse(
                        status = AFirestoreStatusRequest.FAILURE,
                        failure = if (result.exception is FirebaseFirestoreException) {
                            errorResponse(
                                result.exception ?: FirebaseFirestoreException(
                                    FirebaseFirestoreException.Code.UNKNOWN.name,
                                    FirebaseFirestoreException.Code.UNKNOWN
                                )
                            )
                        } else {
                            CUFirestoreErrorEnum.ERROR_DEFAULT
                        }
                    )
                }
            } catch (exception: Exception) {
                "Error getting documents. ${validationError(exception.message!!)}".log(this::class.java.simpleName)
                AFirestoreSetResponse(
                    collectionPath = collectionPath,
                    documentPath = documentPath,
                    failure = validationError(exception.message ?: ""),
                    status = AFirestoreStatusRequest.FAILURE
                )
            }

    }

}