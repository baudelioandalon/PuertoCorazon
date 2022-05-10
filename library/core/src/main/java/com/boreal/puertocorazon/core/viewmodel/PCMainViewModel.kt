package com.boreal.puertocorazon.core.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.boreal.puertocorazon.core.BuildConfig
import com.boreal.puertocorazon.core.domain.entity.AFirestoreGetResponse
import com.boreal.puertocorazon.core.domain.entity.AFirestoreStatusRequest
import com.boreal.puertocorazon.core.domain.entity.auth.AAuthModel
import com.boreal.puertocorazon.core.domain.entity.event.PCEventModel
import com.boreal.puertocorazon.core.usecase.UseCase
import com.boreal.puertocorazon.core.usecase.home.HomeUseCase
import com.boreal.puertocorazon.core.utils.CUBaseViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect

class PCMainViewModel(
    private val getHomeUseCase:
    UseCase<HomeUseCase.Input, HomeUseCase.Output>,
) : CUBaseViewModel() {

    var countOutClicked = 0

    val authUser: LiveData<AAuthModel?>
        get() = _authUser
    private val _authUser = MutableLiveData<AAuthModel?>()

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

    var allowExit = true

    fun setAuthUser(aAuthModel: AAuthModel) {
        _authUser.value = aAuthModel
    }

    fun signOutUser() {
        _authUser.value = AAuthModel()
        _goLogin.value = true
        allowExit = true
    }

    fun setEventSelected(eventModel: PCEventModel) {
        _eventSelected.value = eventModel
    }

    fun getEventSelected() = _eventSelected.value ?: PCEventModel()

    fun getEmailUser() = _authUser.value?.email ?: ""

    fun requestEvents(email: String) {
        executeFlow {
            _eventList.value = AFirestoreGetResponse(status = AFirestoreStatusRequest.LOADING)
            getHomeUseCase.execute(
                HomeUseCase.Input(
                    "eventData",
                    "${BuildConfig.ENVIRONMENT}/$email/Events"
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

}