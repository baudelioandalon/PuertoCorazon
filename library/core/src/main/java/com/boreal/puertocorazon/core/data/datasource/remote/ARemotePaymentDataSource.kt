package com.boreal.puertocorazon.core.data.datasource.remote

import com.boreal.puertocorazon.core.data.GetPaymentDataSource
import com.boreal.puertocorazon.core.domain.entity.payment.PCPaymentRequest
import com.boreal.puertocorazon.core.domain.entity.payment.PCPaymentResponse
import com.boreal.puertocorazon.core.utils.payment.ConektaCardModel
import com.boreal.puertocorazon.core.utils.retrofit.core.DataResponse

class ARemotePaymentDataSource : GetPaymentDataSource {

    override suspend fun getPayment(
        request: PCPaymentRequest,
        conektaModel: ConektaCardModel
    ): DataResponse<PCPaymentResponse> {
        return PaymentDataSource.getData(request, conektaModel)
    }
}