package com.boreal.puertocorazon.addevent.data

import com.boreal.puertocorazon.core.BuildConfig
import com.boreal.puertocorazon.core.domain.entity.AFirestoreRepository
import com.boreal.puertocorazon.core.domain.entity.AFirestoreSetResponse
import com.boreal.puertocorazon.core.domain.entity.AFirestoreStatusRequest
import com.boreal.puertocorazon.core.domain.entity.auth.AAuthModel
import com.boreal.puertocorazon.core.domain.entity.event.PCEventModel
import com.boreal.puertocorazon.core.extension.toModel
import com.boreal.puertocorazon.core.utils.coreauthentication.awaitTask
import com.boreal.puertocorazon.core.utils.corefirestore.errorhandler.CUFirestoreErrorEnum
import com.boreal.puertocorazon.core.utils.realm.getRealmObject
import com.google.firebase.firestore.FirebaseFirestoreException

class AddEventRemoteDataSource {
    companion object : AFirestoreRepository() {
        suspend fun setAddEvent(
            request: PCEventModel
        ): AFirestoreSetResponse<PCEventModel, CUFirestoreErrorEnum> {
            val result = getRealmObject<AAuthModel>().toString().toModel<AAuthModel>()
            return with(
                firestoreInstance.collection(
                    BuildConfig.ENVIRONMENT + result.email + BuildConfig.EVENTS
                )
                    .document(request.idEvent).set(mapOf("eventData" to request)).awaitTask()
            ) {
                try {
                    if (isSuccessful) {
                        AFirestoreSetResponse(
                            modelToSet = request,
                            failure = null,
                            status = AFirestoreStatusRequest.SUCCESS
                        )
                    } else {
                        AFirestoreSetResponse(
                            status = AFirestoreStatusRequest.FAILURE,
                            failure = if (exception is FirebaseFirestoreException) {
                                errorResponse(exception as FirebaseFirestoreException)
                            } else {
                                CUFirestoreErrorEnum.ERROR_DEFAULT
                            }
                        )
                    }
                } catch (exception: Exception) {
                    AFirestoreSetResponse(
                        status = AFirestoreStatusRequest.FAILURE,
                        failure = validationError(exception.message ?: "")
                    )
                }
            }

        }

    }
}