package com.boreal.puertocorazon.core.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.boreal.puertocorazon.core.domain.entity.auth.AAuthModel
import com.boreal.puertocorazon.core.domain.entity.event.PCEventModel
import com.boreal.puertocorazon.core.utils.CUBaseViewModel

class PCMainViewModel : CUBaseViewModel() {

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

    fun removeEventSelected() {
        _eventSelected.value = null
    }

}