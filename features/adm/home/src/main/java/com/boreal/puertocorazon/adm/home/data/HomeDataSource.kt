package com.boreal.puertocorazon.adm.home.data

import android.util.Log
import com.boreal.puertocorazon.core.domain.entity.AFirestoreGetResponse
import com.boreal.puertocorazon.core.domain.entity.AFirestoreRepository
import com.boreal.puertocorazon.core.domain.entity.AFirestoreStatusRequest
import com.boreal.puertocorazon.core.domain.entity.convertDataToList
import com.boreal.puertocorazon.core.domain.entity.event.PCEventModel
import com.boreal.puertocorazon.core.utils.coreauthentication.await
import com.google.firebase.firestore.Source

class HomeDataSource {
    companion object : AFirestoreRepository() {
        suspend fun getEvents(
            idKey: String,
            collectionPath: String
        ): AFirestoreGetResponse<List<PCEventModel>> =
            with(
                firestoreInstance.collection(collectionPath).get(Source.SERVER).await()
            ) {
                try {
                    with(documents.convertDataToList<PCEventModel>(idKey)) {
                        this
                        AFirestoreGetResponse(
                            response = this,
                            failure = null,
                            status = AFirestoreStatusRequest.SUCCESS
                        )
                    }
                } catch (exception: Exception) {
                    Log.e(
                        this::class.java.simpleName,
                        "Error getting documents. ${validationError(exception.message!!)}"
                    )
                    AFirestoreGetResponse(
                        response = null,
                        failure = validationError(exception.message!!),
                        status = AFirestoreStatusRequest.FAILURE
                    )
                }
            }
    }
}