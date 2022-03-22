package com.boreal.puertocorazon.login.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.boreal.puertocorazon.core.domain.entity.AFirestoreAuthResponse
import com.boreal.puertocorazon.core.domain.entity.AFirestoreStatusRequest
import com.boreal.puertocorazon.core.domain.entity.auth.AAuthLoginEmailModel
import com.boreal.puertocorazon.core.domain.entity.auth.AAuthModel
import com.boreal.puertocorazon.core.usecase.UseCase
import com.boreal.puertocorazon.core.utils.CUBaseViewModel
import com.boreal.puertocorazon.core.utils.corefirestore.errorhandler.CUAuthenticationErrorEnum
import com.boreal.puertocorazon.login.usecase.LoginUseCase
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.collect

class ALoginViewModel(private val getLoginUseCase: UseCase<LoginUseCase.Input, LoginUseCase.Output>) :
    CUBaseViewModel() {

    val loginData: LiveData<AFirestoreAuthResponse<AAuthLoginEmailModel, AAuthModel, CUAuthenticationErrorEnum>>
        get() = _loginData
    private val _loginData =
        MutableLiveData<AFirestoreAuthResponse<AAuthLoginEmailModel, AAuthModel, CUAuthenticationErrorEnum>>()

    fun requestLogin(request: AAuthLoginEmailModel) {
        executeFlow {
            _loginData.postValue(
                AFirestoreAuthResponse(
                    status = AFirestoreStatusRequest.LOADING,
                    authModel = request
                )
            )
            getLoginUseCase.execute(LoginUseCase.Input(request)).collect {
                _loginData.postValue(it.response)
            }
        }
    }

}