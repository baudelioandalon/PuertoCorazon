package com.boreal.puertocorazon.core.domain

import com.boreal.puertocorazon.core.domain.entity.payment.PCPaymentRequest
import com.boreal.puertocorazon.core.domain.entity.payment.PCPaymentResponse
import com.boreal.puertocorazon.core.utils.retrofit.core.DataResponse
import kotlinx.coroutines.flow.Flow

interface PaymentRepository {
    suspend fun getPayment(
        request: PCPaymentRequest
    ): Flow<DataResponse<PCPaymentResponse>>
}