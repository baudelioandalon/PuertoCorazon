package com.boreal.puertocorazon.adm.checking.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.boreal.puertocorazon.adm.checking.usecase.CheckingUseCase
import com.boreal.puertocorazon.core.BuildConfig
import com.boreal.puertocorazon.core.domain.entity.AFirestoreSetResponse
import com.boreal.puertocorazon.core.domain.entity.AFirestoreStatusRequest
import com.boreal.puertocorazon.core.usecase.login.UseCase
import com.boreal.puertocorazon.core.utils.CUBaseViewModel
import com.boreal.puertocorazon.core.utils.corefirestore.errorhandler.CUFirestoreErrorEnum
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect

class PCCheckingViewModel(
    private val updateCheckingUseCase: UseCase<CheckingUseCase.Input, CheckingUseCase.Output>,
) : CUBaseViewModel() {

    val updateChecking: LiveData<AFirestoreSetResponse<ArrayList<Map<String, Any>>, CUFirestoreErrorEnum>>
        get() = _updateChecking
    private val _updateChecking =
        MutableLiveData<AFirestoreSetResponse<ArrayList<Map<String, Any>>, CUFirestoreErrorEnum>>()

    fun updateTicket(idTicket: String, model: ArrayList<Map<String, Any>>) {
        executeFlow {
            _updateChecking.postValue(
                AFirestoreSetResponse(
                    status = AFirestoreStatusRequest.LOADING
                )
            )
            updateCheckingUseCase.execute(
                CheckingUseCase.Input(
                    checkingModel = model,
                    collectionPath = "${BuildConfig.ENVIRONMENT}${BuildConfig.DEFAULT_EMAIL}${BuildConfig.TICKETS}",
                    documentPath = idTicket
                )
            ).collect {
                _updateChecking.postValue(it.response)
            }
        }
    }

    fun resetViewModel() {
        _updateChecking.postValue(AFirestoreSetResponse())
    }

}