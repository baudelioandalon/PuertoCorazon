package com.boreal.puertocorazon.core.domain.entity

import com.boreal.puertocorazon.core.utils.corefirestore.errorhandler.CUFirestoreErrorEnum

data class AFirestoreGetResponse<R>(
    val response: R? = null,
    val failure: CUFirestoreErrorEnum? = null,
    val status: AFirestoreStatusRequest = AFirestoreStatusRequest.NONE
)