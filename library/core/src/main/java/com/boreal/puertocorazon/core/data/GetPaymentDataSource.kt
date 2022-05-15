package com.boreal.puertocorazon.core.data

import com.boreal.puertocorazon.core.domain.entity.payment.PCPaymentRequest
import com.boreal.puertocorazon.core.domain.entity.payment.PCPaymentResponse
import com.boreal.puertocorazon.core.utils.payment.ConektaCardModel
import com.boreal.puertocorazon.core.utils.retrofit.core.DataResponse

interface GetPaymentDataSource {
    suspend fun getPayment(
        request: PCPaymentRequest,
        conektaModel: ConektaCardModel
    ): DataResponse<PCPaymentResponse>
}