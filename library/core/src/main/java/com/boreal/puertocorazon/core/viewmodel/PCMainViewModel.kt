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

//    /**
//     * @see Obtener lista de clientes
//     */
//    val dataClients = AFirestoreGetResponse<ArrayList<ACompleteDataUserModel>, CUFirestoreErrorEnum>(
//        idKey = ATypeOfUser.CLIENT.data,
//        collectionPath = BuildConfig.ENVIRONMENT + AAuth.authInstance.currentUser?.email + "/" + BuildConfig.CLIENTS
//    )
//
//    /**
//     * @see Obtener lista de gestores
//     */
//    val dataLenders = AFirestoreGetResponse<ArrayList<ACompleteDataUserModel>, CUFirestoreErrorEnum>(
//        idKey = ATypeOfUser.LENDER.data,
//        collectionPath = BuildConfig.ENVIRONMENT + AAuth.authInstance.currentUser?.email + "/" + BuildConfig.LENDERS
//    )
//
//    /**
//     * @see Obtener informacion del cliente seleccionado
//     */
//    val dataClientProfile = AFirestoreGetResponse<ACommonUserDataModel, CUFirestoreErrorEnum>(
//        idKey = ATypeOfUser.CLIENT.data,
//        collectionPath = BuildConfig.ENVIRONMENT + AAuth.authInstance.currentUser?.email + "/" + BuildConfig.CLIENTS
//    )
//
//    /**
//     * @see Obtener informacion del gestor seleccionado
//     */
//    val dataLenderProfile = AFirestoreGetResponse<ACommonUserDataModel, CUFirestoreErrorEnum>(
//        idKey = ATypeOfUser.LENDER.data,
//        collectionPath = BuildConfig.ENVIRONMENT + AAuth.authInstance.currentUser?.email + "/" + BuildConfig.LENDERS
//    )
//
//    var typeUserProfileSelected = MutableLiveData(ATypeOfUser.CLIENT)
//
//    val userInSession = AFirestoreGetResponse<AUserModel, CUFirestoreErrorEnum>().apply {
//        idKey = ATypeOfUser.USER.data
//        collectionPath = BuildConfig.ENVIRONMENT + AAuth.authInstance.currentUser?.email
//        documentPath = BuildConfig.ENVIRONMENT + AAuth.authInstance.currentUser?.email
//    }


}