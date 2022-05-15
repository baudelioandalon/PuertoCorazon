package com.boreal.puertocorazon.core.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.boreal.puertocorazon.core.BuildConfig
import com.boreal.puertocorazon.core.constants.NONE
import com.boreal.puertocorazon.core.domain.entity.AFirestoreGetResponse
import com.boreal.puertocorazon.core.domain.entity.AFirestoreStatusRequest
import com.boreal.puertocorazon.core.domain.entity.auth.AAuthModel
import com.boreal.puertocorazon.core.domain.entity.event.PCEventModel
import com.boreal.puertocorazon.core.domain.entity.payment.PCCardModel
import com.boreal.puertocorazon.core.domain.entity.payment.PCPaymentRequest
import com.boreal.puertocorazon.core.domain.entity.payment.PCPaymentResponse
import com.boreal.puertocorazon.core.domain.entity.payment.PCTypeCard
import com.boreal.puertocorazon.core.domain.entity.shopping.PCShoppingModel
import com.boreal.puertocorazon.core.usecase.UseCase
import com.boreal.puertocorazon.core.usecase.home.HomeUseCase
import com.boreal.puertocorazon.core.usecase.payment.PaymentUseCase
import com.boreal.puertocorazon.core.utils.CUBaseViewModel
import com.boreal.puertocorazon.core.utils.payment.ConektaCardModel
import com.boreal.puertocorazon.core.utils.retrofit.core.DataResponse
import com.boreal.puertocorazon.core.utils.retrofit.core.StatusRequestEnum
import com.google.firebase.auth.FirebaseAuth
import io.realm.Realm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class PCMainViewModel(
    private val getHomeUseCase: UseCase<HomeUseCase.Input, HomeUseCase.Output>,
    private val getPaymentUseCase: UseCase<PaymentUseCase.Input, PaymentUseCase.Output>,
) : CUBaseViewModel() {

    var countOutClicked = 0

    val authUser: LiveData<AAuthModel?>
        get() = _authUser
    private val _authUser = MutableLiveData<AAuthModel?>()

    private val cardList = arrayListOf<PCCardModel>()

    private var shoppingCart = arrayListOf(
        PCShoppingModel(
            idEvent = "dmuc874hc8348",
            titleEvent = "Evento Holistico",
            isPackage = true,
            namePackage = "Paquete familia",
            countAdult = 2,
            countChild = 2,
            priceElement = 450
        ), PCShoppingModel(
            idEvent = "v45gc32ed",
            titleEvent = "2do Evento",
            countAdult = 1,
            priceElement = 150
        )
    )

    fun getCardList() = cardList

    var shoppingChanged: ((list: List<PCShoppingModel>) -> Unit)? = null

    fun addShopping(element: PCShoppingModel) {
        shoppingCart.add(element)
        shoppingChanged?.invoke(shoppingCart)
    }

    fun setShoppingList(list: ArrayList<PCShoppingModel>) {
        shoppingCart = list
        shoppingChanged?.invoke(shoppingCart)
    }

    fun clearShoppingCart() {
        shoppingCart.clear()
        shoppingChanged?.invoke(shoppingCart)
    }

    fun getShoppingList() = shoppingCart

    val goLogin: LiveData<Boolean?>
        get() = _goLogin
    private val _goLogin = MutableLiveData<Boolean>()

    val eventSelected: LiveData<PCEventModel?>
        get() = _eventSelected
    private val _eventSelected = MutableLiveData<PCEventModel?>()

    val eventList: LiveData<AFirestoreGetResponse<List<PCEventModel>>>
        get() = _eventList
    private val _eventList =
        MutableLiveData<AFirestoreGetResponse<List<PCEventModel>>>()

    val paymentTransaction: LiveData<DataResponse<PCPaymentResponse>>
        get() = _paymentTransaction
    private val _paymentTransaction = MutableLiveData<DataResponse<PCPaymentResponse>>()

    var allowExit = true
    var logOut = false

    fun setAuthUser(aAuthModel: AAuthModel?) {
        _authUser.value = aAuthModel
    }

    fun signOutUser() {
        viewModelScope.launch(Dispatchers.IO) {
            Realm.getDefaultInstance().executeTransaction {
                FirebaseAuth.getInstance().signOut()
                it.deleteAll()
                _authUser.postValue(AAuthModel())
                _goLogin.postValue(true)
                allowExit = true
            }
        }
    }

    fun setEventSelected(eventModel: PCEventModel) {
        _eventSelected.value = eventModel
    }

    fun getEventSelected() = _eventSelected.value ?: PCEventModel()

    fun getEmailUser() = _authUser.value?.email ?: NONE
    fun getImageProfile() = _authUser.value?.picture ?: NONE

    fun requestEvents(email: String) {
        executeFlow {
            _eventList.value = AFirestoreGetResponse(status = AFirestoreStatusRequest.LOADING)
            if (_eventList.value?.status == AFirestoreStatusRequest.SUCCESS) {
                return@executeFlow
            }
            getHomeUseCase.execute(
                HomeUseCase.Input(
                    "eventData",
                    "${BuildConfig.ENVIRONMENT}${BuildConfig.DEFAULT_EMAIL}Events"
                )
            ).catch { cause: Throwable ->
                cause
            }.collect {
                _eventList.value = it.response
            }
        }
    }

    fun removeEventSelected() {
        _eventSelected.value = null
    }

    fun requestPayment(aliasCard: String, conektaCardModel: ConektaCardModel) {
        executeFlow {
            _paymentTransaction.value = DataResponse(statusRequest = StatusRequestEnum.LOADING)
            getPaymentUseCase.execute(
                PaymentUseCase.Input(
                    PCPaymentRequest(
                        countAdult = 1,
                        countChild = 1,
                        idEvent = "1378950eafce4ed3a7797a2f9732b1c7",
                        idClient = "epQUrsON4aNxzxqizDgSZlc3whX2",//baudelioandalon@gmail.com,
                        namePackage = "Familia",
                        nameUser = "Baudelio Andalon",
                        email = "baudelioandalon@gmail.com",
                        isPackage = true,
                        amount = 50,
                        typeCard = PCTypeCard.VISA.name,
                        lastFour = "1414",
                        typePayment = "CARD",
                        countItem = 1
                    ), conektaCardModel
                )
            ).catch {
                _paymentTransaction.value = DataResponse(
                    statusRequest = StatusRequestEnum.FAILURE,
                    null, it.message ?: "Algo salio mal"
                )
            }.collect {
                _paymentTransaction.postValue(it.response)
            }
        }
    }

    fun generateToken(token: String) {
        if (token.isNotEmpty()) {
//                        requestPayment(response)
        }
    }

    private fun errorInternetPayment() {
        _paymentTransaction.postValue(
            DataResponse(
                statusRequest = StatusRequestEnum.FAILURE,
                null, "Verifica tu conexion a internet"
            )
        )
        resetError()
    }

    private fun resetError() {
        _paymentTransaction.postValue(
            DataResponse(
                statusRequest = StatusRequestEnum.NONE,
                null, null
            )
        )
    }

}