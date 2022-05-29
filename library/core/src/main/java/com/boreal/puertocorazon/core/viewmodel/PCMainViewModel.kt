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
import com.boreal.puertocorazon.core.domain.entity.payment.PCPackageTicketModel
import com.boreal.puertocorazon.core.domain.entity.payment.PCPaymentRequest
import com.boreal.puertocorazon.core.domain.entity.payment.PCPaymentResponse
import com.boreal.puertocorazon.core.domain.entity.shopping.PCShoppingModel
import com.boreal.puertocorazon.core.usecase.event.EventUseCase
import com.boreal.puertocorazon.core.usecase.home.HomeUseCase
import com.boreal.puertocorazon.core.usecase.login.UseCase
import com.boreal.puertocorazon.core.usecase.payment.PaymentUseCase
import com.boreal.puertocorazon.core.usecase.ticket.TicketUseCase
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
    private val getTicketUseCase: UseCase<TicketUseCase.Input, TicketUseCase.Output>,
    private val getEventUseCase: UseCase<EventUseCase.Input, EventUseCase.Output>
) : CUBaseViewModel() {

    var countOutClicked = 0

    val authUser: LiveData<AAuthModel?>
        get() = _authUser
    private val _authUser = MutableLiveData<AAuthModel?>()

    private val cardList = arrayListOf<PCCardModel>()

    var goToHomeClient: (() -> Unit)? = null

    fun goToMenuHome() {
        goToHomeClient?.invoke()
    }

    private var shoppingCart = arrayListOf<PCShoppingModel>()
//        PCShoppingModel(
//            idEvent = "dmuc874hc8348",
//            titleEvent = "Evento Holistico",
//            isPackage = true,
//            namePackage = "Paquete familia",
//            countAdult = 2,
//            countChild = 2,
//            priceElement = 450
//        ), PCShoppingModel(
//            idEvent = "v45gc32ed",
//            titleEvent = "2do Evento",
//            countAdult = 1,
//            priceElement = 150
//        )
//    )

    fun getCardList() = cardList

    var shoppingChanged: ((list: List<PCShoppingModel>) -> Unit)? = null
    var navToTicket: () -> Unit = {}
    var navToHome: () -> Unit = {}

    fun addShopping(element: PCShoppingModel) {
        val founded = shoppingCart.find { it.idPackage == element.idPackage }
        if (founded != null) {
            shoppingCart.removeAt(shoppingCart.indexOf(founded))
            founded.countItem += 1
            shoppingCart.add(founded)
        } else {
            shoppingCart.add(element)
        }
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

    fun paymentClear() {
        resetError()
    }

    fun getShoppingList() = shoppingCart
    private fun getShoppingListToMap() = with(ArrayList<PCPackageTicketModel>()) {
        getShoppingList().forEach { element ->
            repeat(element.countItem) {
                add(
                    PCPackageTicketModel(
                        countAdult = element.countAdult.toLong(),
                        countChild = element.countChild.toLong(),
                        idClient = getIdUser(),
                        idEvent = element.idEvent,
                        namePackage = element.namePackage,
                        isPackage = element.isPackage,
                        priceItem = element.priceElement.toFloat().toLong(),
                        nameEvent = element.titleEvent,
                        imageEvent = element.imageEvent,
                        idPackage = element.idPackage
                    )
                )
            }

        }
        this
    }


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

    val singleEvent: LiveData<AFirestoreGetResponse<PCEventModel>>
        get() = _singleEvent
    private val _singleEvent =
        MutableLiveData<AFirestoreGetResponse<PCEventModel>>()

    val ticketList: LiveData<AFirestoreGetResponse<List<PCPackageTicketModel>>>
        get() = _ticketList
    private val _ticketList =
        MutableLiveData<AFirestoreGetResponse<List<PCPackageTicketModel>>>()

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
                _ticketList.postValue(AFirestoreGetResponse())
                _eventList.postValue(AFirestoreGetResponse())
                _paymentTransaction.postValue(DataResponse())
                _authUser.postValue(null)
                _goLogin.postValue(true)
                allowExit = true
            }
        }
    }

    fun setEventSelected(eventModel: PCEventModel) {
        _eventSelected.value = eventModel
    }

    fun getEventSelected() = _eventSelected.value ?: PCEventModel()

    fun getIdUser() = _authUser.value?.user_id ?: NONE
    fun getEmailUser() = _authUser.value?.email ?: NONE
    fun getImageProfile() = _authUser.value?.picture ?: NONE

    fun requestEvents() {
        executeFlow {
            if (_eventList.value?.status == AFirestoreStatusRequest.SUCCESS ||
                _eventList.value?.status == AFirestoreStatusRequest.LOADING
            ) {
                return@executeFlow
            }
            _eventList.value = AFirestoreGetResponse(status = AFirestoreStatusRequest.LOADING)
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

    fun requestTickets() {
        executeFlow {
            if (_ticketList.value?.status == AFirestoreStatusRequest.SUCCESS ||
                _ticketList.value?.status == AFirestoreStatusRequest.LOADING
            ) {
                return@executeFlow
            }
            _ticketList.value = AFirestoreGetResponse(status = AFirestoreStatusRequest.LOADING)
            getTicketUseCase.execute(
                TicketUseCase.Input(
                    getIdUser(),
                    "${BuildConfig.ENVIRONMENT}${BuildConfig.DEFAULT_EMAIL}Tickets"
                )
            ).catch { cause: Throwable ->
                cause
            }.collect {
                _ticketList.value = it.response
            }
        }
    }

    fun getAllTicketsClient() = _ticketList.value?.response ?: arrayListOf()

    fun removeEventSelected() {
        _eventSelected.value = null
    }

    fun requestPayment(aliasCard: String, conektaCardModel: ConektaCardModel) {
        executeFlow {
            _paymentTransaction.value = DataResponse(statusRequest = StatusRequestEnum.LOADING)
            getPaymentUseCase.execute(
                PaymentUseCase.Input(
                    PCPaymentRequest(
                        idClient = getIdUser(),
                        nameUser = "Baudelio Andalon",
                        email = getEmailUser(),
                        amount = getShoppingList().sumOf { (it.countItem * it.priceElement) }
                            .toLong(),
                        typeCard = conektaCardModel.typeCard(),
                        lastFour = conektaCardModel.lastFour(),
                        typePayment = "CARD",
                        phone = "1234567891",
                        aliasCard = aliasCard,
                        expirationDate = conektaCardModel.expirationDate(),
                        digitsCard = conektaCardModel.numberCard,
                        emailLocal = BuildConfig.DEFAULT_EMAIL,
                        environmentLocal = BuildConfig.ENVIRONMENT,
                        packages = getShoppingListToMap()
                    ), conektaCardModel
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

    fun requestSingleEvent(idEventToSearch: String) {
        executeFlow {
            if (_singleEvent.value?.status == AFirestoreStatusRequest.LOADING) {
                return@executeFlow
            }
            _singleEvent.value = AFirestoreGetResponse(status = AFirestoreStatusRequest.LOADING)
            getEventUseCase.execute(
                EventUseCase.Input(
                    idEventToSearch,
                    "${BuildConfig.ENVIRONMENT}${BuildConfig.DEFAULT_EMAIL}Events"
                )
            ).catch { cause: Throwable ->
                cause
            }.collect {
                _singleEvent.value = it.response
            }
        }
    }

    fun navigateToTicket() {
        navToTicket.invoke()
    }

    fun navigateToHome() {
        navToHome.invoke()
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