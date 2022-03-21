package com.boreal.puertocorazon.core.domain.entity

import androidx.lifecycle.MutableLiveData

data class AFirestoreSetResponse<R, E>(
    var idField: String = "",
    var collectionPath: String = "",
    var documentPath: String = "",
    var modelToSet: R? = null,
    val failure: E? = null,
    val status: AFirestoreStatusRequest = AFirestoreStatusRequest.NONE
)

fun <R,E> MutableLiveData<AFirestoreSetResponse<R, E>>.insertFirestore(
    valueToInsert: R,
    execute: (onChange: AFirestoreSetResponse<R, E>) -> Unit
){
    with(
        AFirestoreSetResponse(
            this.value?.idField ?: "",
            this.value?.collectionPath ?: "",
            this.value?.documentPath ?: "",
            valueToInsert,
            this.value?.failure,
            AFirestoreStatusRequest.LOADING
        )
    ) {
        postValue(
            this
        )
        execute.invoke(this)
    }
}