package com.boreal.puertocorazon.core.utils.retrofit.core

import com.boreal.puertocorazon.core.domain.entity.payment.PCPaymentErrorModel

data class DataResponse<R>(
    val statusRequest: StatusRequestEnum = StatusRequestEnum.NONE,
    val successData: R? = null,
    val errorModel: PCPaymentErrorModel? = null,
    var errorData: String? = null
)
