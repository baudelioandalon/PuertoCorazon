package com.boreal.puertocorazon.payments.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.boreal.puertocorazon.core.BuildConfig
import com.boreal.puertocorazon.core.domain.entity.payment.PCPackageTicketModel
import com.boreal.puertocorazon.core.domain.entity.payment.PCPaymentRequest
import com.boreal.puertocorazon.core.domain.entity.payment.PCPaymentResponse
import com.boreal.puertocorazon.core.usecase.login.UseCase
import com.boreal.puertocorazon.core.usecase.payment.PaymentUseCase
import com.boreal.puertocorazon.core.utils.CUBaseViewModel
import com.boreal.puertocorazon.core.utils.retrofit.core.DataResponse
import com.boreal.puertocorazon.core.utils.retrofit.core.StatusRequestEnum
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect

class PCCartShoppingViewModel(private val getPaymentUseCase: UseCase<PaymentUseCase.Input, PaymentUseCase.Output>) :
    CUBaseViewModel() {
    //    "idClient": "43cfr453f2234f",
//    "nameUser": "Baudelio Andalon",
//    "email": "baudelioandalon@gmail.com",
//    "emailDefault":"administrator@borealnetwork.org",
//    "environment":"DEBUG",
//    "amount":50,
//    "packages":[

    val paymentTransaction: LiveData<DataResponse<PCPaymentResponse>>
        get() = _paymentTransaction
    private val _paymentTransaction = MutableLiveData<DataResponse<PCPaymentResponse>>()


    fun requestPayment(
        idClient: String,
        nameUser: String,
        emailUser: String,
        iva: Long,
        packagesItems: ArrayList<PCPackageTicketModel>
    ) {
        val subtotal = packagesItems.sumOf { (it.countItem * it.priceItem) }
        executeFlow {
            _paymentTransaction.value = DataResponse(statusRequest = StatusRequestEnum.LOADING)
            getPaymentUseCase.execute(
                PaymentUseCase.Input(
                    PCPaymentRequest(
                        idClient = idClient,
                        nameUser = nameUser,
                        email = emailUser,
                        amount = subtotal + iva,
                        emailDefault = BuildConfig.DEFAULT_EMAIL.replace("/", ""),
                        environment = BuildConfig.ENVIRONMENT.replace("/", ""),
                        packages = packagesItems
                    )
                )
            ).catch {
                _paymentTransaction.value = DataResponse(
                    statusRequest = StatusRequestEnum.FAILURE,
                    null, errorData = it.message ?: "Algo salio mal"
                )
            }.collect {
                _paymentTransaction.postValue(it.response)
            }
        }
    }

    fun paymentClear() {
        resetError()
    }

    private fun resetError() {
        _paymentTransaction.postValue(
            DataResponse(
                statusRequest = StatusRequestEnum.NONE,
                null
            )
        )
    }
}