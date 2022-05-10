package com.boreal.puertocorazon.adm.home.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.boreal.puertocorazon.core.domain.entity.AFirestoreStatusRequest
import com.boreal.puertocorazon.core.domain.entity.auth.AAuthModel
import com.boreal.puertocorazon.core.usecase.AuthUseCase
import com.boreal.puertocorazon.core.usecase.EmptyIn
import com.boreal.puertocorazon.core.usecase.UseCase
import com.boreal.puertocorazon.core.utils.CUBaseViewModel
import kotlinx.coroutines.flow.collect

class PCHomeViewModel(
    private val getAuthUseCase:
    UseCase<EmptyIn, AuthUseCase.Output>
) : CUBaseViewModel() {

    val authUser: LiveData<Pair<AFirestoreStatusRequest, AAuthModel?>>
        get() = _authUser
    private val _authUser = MutableLiveData<Pair<AFirestoreStatusRequest, AAuthModel?>>(
        Pair(
            AFirestoreStatusRequest.NONE,
            null
        )
    )

    fun getLocalUser() {
        executeFlow {
            _authUser.value = Pair(AFirestoreStatusRequest.LOADING, AAuthModel())
            getAuthUseCase.execute(EmptyIn).collect {
                _authUser.value = it.response
            }
        }
    }

}