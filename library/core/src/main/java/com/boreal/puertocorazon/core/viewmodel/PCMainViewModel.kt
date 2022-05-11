package com.boreal.puertocorazon.core.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.boreal.commonutils.component.dialogs.blurdialog.CUBlurDialog
import com.boreal.commonutils.extensions.onClick
import com.boreal.puertocorazon.core.BuildConfig
import com.boreal.puertocorazon.core.constants.NONE
import com.boreal.puertocorazon.core.domain.entity.AFirestoreGetResponse
import com.boreal.puertocorazon.core.domain.entity.AFirestoreStatusRequest
import com.boreal.puertocorazon.core.domain.entity.auth.AAuthModel
import com.boreal.puertocorazon.core.domain.entity.event.PCEventModel
import com.boreal.puertocorazon.core.usecase.UseCase
import com.boreal.puertocorazon.core.usecase.home.HomeUseCase
import com.boreal.puertocorazon.core.utils.CUBaseViewModel
import com.boreal.puertocorazon.core.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import io.realm.Realm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

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

}