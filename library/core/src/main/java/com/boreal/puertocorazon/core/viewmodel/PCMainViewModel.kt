package com.boreal.puertocorazon.core.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.boreal.puertocorazon.core.BuildConfig
import com.boreal.puertocorazon.core.constants.NONE
import com.boreal.puertocorazon.core.domain.entity.AFirestoreGetResponse
import com.boreal.puertocorazon.core.domain.entity.AFirestoreStatusRequest
import com.boreal.puertocorazon.core.domain.entity.auth.AAuthModel
import com.boreal.puertocorazon.core.domain.entity.auth.PCUserType
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
import com.boreal.puertocorazon.core.usecase.ticket.TicketByClientUseCase
import com.boreal.puertocorazon.core.usecase.ticket.TicketByEventUseCase
import com.boreal.puertocorazon.core.utils.CUBaseViewModel
import com.boreal.puertocorazon.core.utils.payment.ConektaCardModel
import com.boreal.puertocorazon.core.utils.retrofit.core.DataResponse
import com.boreal.puertocorazon.core.utils.retrofit.core.StatusRequestEnum
import com.google.firebase.auth.FirebaseAuth
import io.realm.Realm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class PCMainViewModel(
    private val getHomeUseCase: UseCase<HomeUseCase.Input, HomeUseCase.Output>,
    private val getPaymentUseCase: UseCase<PaymentUseCase.Input, PaymentUseCase.Output>,
    private val getTicketByClientUseCase: UseCase<TicketByClientUseCase.Input, TicketByClientUseCase.Output>,
    private val getEventUseCase: UseCase<EventUseCase.Input, EventUseCase.Output>,
    private val getTicketByEventUseCase: UseCase<TicketByEventUseCase.Input, TicketByEventUseCase.Output>
) : CUBaseViewModel() {

    var countOutClicked = 0

    val authUser: LiveData<AAuthModel?>
        get() = _authUser
    private val _authUser = MutableLiveData<AAuthModel?>()

    private val cardList = arrayListOf<PCCardModel>()

    var goToHomeClient: (() -> Unit)? = null
    var goToChecking: (() -> Unit)? = null
    var goToPayment: (() -> Unit)? = null
    var splash: ((show: Boolean) -> Unit)? = null
    var resetLogin = true

    fun goToMenuHome() {
        goToHomeClient?.invoke()
    }

    fun goToChecking() {
        goToChecking?.invoke()
    }

    fun goToPayments() {
        goToPayment?.invoke()
    }

    fun showSplash() {
        viewModelScope.launch(Dispatchers.Main) {
            splash?.invoke(true)
        }
    }

    fun hideSplash(clicked: () -> Unit = {}) {
        viewModelScope.launch(Dispatchers.Main) {
            delay(500)
            splash?.invoke(false)
            clicked()
        }
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
    var navToMap: () -> Unit = {}

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
    var checkingSelected: PCEventModel? = null

    val eventList: LiveData<AFirestoreGetResponse<List<PCEventModel>>>
        get() = _eventList
    private val _eventList =
        MutableLiveData<AFirestoreGetResponse<List<PCEventModel>>>()

    val singleEvent: LiveData<AFirestoreGetResponse<PCEventModel>>
        get() = _singleEvent
    private val _singleEvent =
        MutableLiveData<AFirestoreGetResponse<PCEventModel>>()

    val ticketListByClient: LiveData<AFirestoreGetResponse<List<PCPackageTicketModel>>>
        get() = _ticketListByClient
    private val _ticketListByClient =
        MutableLiveData<AFirestoreGetResponse<List<PCPackageTicketModel>>>()

    val ticketListByEvent: LiveData<AFirestoreGetResponse<List<PCPackageTicketModel>>>
        get() = _ticketListByEvent
    private val _ticketListByEvent =
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
            showSplash()
            Realm.getDefaultInstance().executeTransaction {
                FirebaseAuth.getInstance().signOut()
                it.deleteAll()
                _ticketListByClient.postValue(AFirestoreGetResponse())
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

    fun setCheckingEvent(eventModel: PCEventModel) {
        checkingSelected = eventModel
        goToChecking()
    }

    fun getEventSelected() = _eventSelected.value ?: checkingSelected ?: PCEventModel()

    fun getIdUser() = _authUser.value?.user_id ?: NONE
    fun getEmailUser() = _authUser.value?.email ?: NONE
    fun getNameUser() = _authUser.value?.name ?: NONE
    fun getImageProfile() = _authUser.value?.picture ?: NONE
    fun getTypeUser() = _authUser.value?.userType ?: PCUserType.NONE.type

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

    fun requestTicketsByClient() {
        executeFlow {
            if (_ticketListByClient.value?.status == AFirestoreStatusRequest.SUCCESS ||
                _ticketListByClient.value?.status == AFirestoreStatusRequest.LOADING
            ) {
                return@executeFlow
            }
            _ticketListByClient.value =
                AFirestoreGetResponse(status = AFirestoreStatusRequest.LOADING)
            getTicketByClientUseCase.execute(
                TicketByClientUseCase.Input(
                    getIdUser(),
                    "${BuildConfig.ENVIRONMENT}${BuildConfig.DEFAULT_EMAIL}Tickets"
                )
            ).catch { cause: Throwable ->
                cause
            }.collect {
                _ticketListByClient.value = it.response
            }
        }
    }

    fun requestTicketsByEvent() {
        executeFlow {
            if (_ticketListByEvent.value?.status == AFirestoreStatusRequest.SUCCESS ||
                _ticketListByEvent.value?.status == AFirestoreStatusRequest.LOADING
            ) {
                return@executeFlow
            }
            _ticketListByEvent.value =
                AFirestoreGetResponse(status = AFirestoreStatusRequest.LOADING)
            getTicketByEventUseCase.execute(
                TicketByEventUseCase.Input(
                    getEventSelected().idEvent,
                    "${BuildConfig.ENVIRONMENT}${BuildConfig.DEFAULT_EMAIL}Tickets"
                )
            ).catch { cause: Throwable ->
                cause
            }.collect {
                _ticketListByEvent.value = it.response
            }
        }
    }

    fun getAllTicketsByEvent() = _ticketListByEvent.value?.response ?: arrayListOf()

    fun getAllTicketsClient() = _ticketListByClient.value?.response ?: arrayListOf()

    fun getAllTicketsClientFiltered(model: PCPackageTicketModel) =
        _ticketListByClient.value?.response?.filter {
            it.isPackage == model.isPackage
                    && it.idEvent == model.idEvent
                    && it.namePackage == model.namePackage
                    && it.idPackage == model.idPackage
        }?.sortedBy { it.isUsed() } ?: emptyList()

    fun removeEventSelected() {
        _eventSelected.value = null
    }

    fun removeCheckingSelected() {
        checkingSelected = null
    }

    fun requestPayment(nameCard: String, aliasCard: String, conektaCardModel: ConektaCardModel) {
        executeFlow {
            _paymentTransaction.value = DataResponse(statusRequest = StatusRequestEnum.LOADING)
            getPaymentUseCase.execute(
                PaymentUseCase.Input(
                    PCPaymentRequest(
                        idClient = getIdUser(),
                        nameUser = nameCard,
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
                    "${BuildConfig.ENVIRONMENT}${BuildConfig.DEFAULT_EMAIL}Events", true
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

    fun navigateToMap(){
        navToMap.invoke()
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