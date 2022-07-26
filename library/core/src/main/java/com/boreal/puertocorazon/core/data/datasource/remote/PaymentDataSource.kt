package com.boreal.puertocorazon.core.data.datasource.remote

import com.boreal.puertocorazon.core.domain.entity.payment.PCPaymentRequest
import com.boreal.puertocorazon.core.domain.entity.payment.PCPaymentResponse
import com.boreal.puertocorazon.core.utils.retrofit.core.*
import retrofit2.Call
import java.net.UnknownHostException


class PaymentDataSource {

    companion object {
        fun getData(
            request: PCPaymentRequest
        ): DataResponse<PCPaymentResponse> = try {

            request.email = request.email.replace("/", "")
            request.emailDefault = request.emailDefault.replace("/", "")
            ValidResponse<PCPaymentResponse>(PCPaymentResponse::class).validationMethod(
                InitServices<Call<PCPaymentResponse>, PCPaymentRequest>().postExecuteService(
                    request,
                    RemoteUrls.PAYMENT_TRANSACTION.url
                )
            )
        } catch (exception: Exception) {
            if (exception is UnknownHostException) {
                DataResponse(
                    statusRequest = StatusRequestEnum.FAILURE,
                    null,
                    errorData = "Por favor, revisa tu conexion a internet"
                )
            } else {
                DataResponse(
                    statusRequest = StatusRequestEnum.FAILURE,
                    null,
                    errorData = "No se pudo completar la transaccion, por favor intenta mas tarde"
                )
            }
        }
    }

}
