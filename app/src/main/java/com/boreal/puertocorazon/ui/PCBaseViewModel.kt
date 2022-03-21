package com.boreal.puertocorazon.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PCBaseViewModel : ViewModel() {

    var countOutClicked = 0

    /**
     * @see Obtener lista de clientes
     */
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