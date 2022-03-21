package com.boreal.puertocorazon.login.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.boreal.puertocorazon.core.domain.AFirestoreAuthResponse
import com.boreal.puertocorazon.core.domain.entity.auth.AAuthLoginEmailModel
import com.boreal.puertocorazon.core.usecase.UseCase
import com.boreal.puertocorazon.core.utils.corefirestore.errorhandler.CUAuthenticationErrorEnum
import com.boreal.puertocorazon.core.viewmodel.CUBaseViewModel
import com.boreal.puertocorazon.login.ui.login.usecase.LoginUseCase
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.collect

class ALoginViewModel(private val getLoginUseCase: UseCase<LoginUseCase.Input, LoginUseCase.Output>) :
    CUBaseViewModel() {

    val loginData: LiveData<AFirestoreAuthResponse<AAuthLoginEmailModel, Task<AuthResult>, CUAuthenticationErrorEnum>>
        get() = _loginData
    private val _loginData =
        MutableLiveData<AFirestoreAuthResponse<AAuthLoginEmailModel, Task<AuthResult>, CUAuthenticationErrorEnum>>()

    fun requestLogin(request: AAuthLoginEmailModel) {
        executeFlow {
            getLoginUseCase.execute(LoginUseCase.Input(request)).collect {
                _loginData.postValue(it.response)
            }
        }

    }

}