package com.boreal.puertocorazon.login.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.boreal.puertocorazon.core.domain.entity.AFirestoreAuthResponse
import com.boreal.puertocorazon.core.domain.entity.AFirestoreStatusRequest
import com.boreal.puertocorazon.core.domain.entity.auth.AAuthLoginEmailModel
import com.boreal.puertocorazon.core.domain.entity.auth.AAuthModel
import com.boreal.puertocorazon.core.usecase.EmptyIn
import com.boreal.puertocorazon.core.usecase.UseCase
import com.boreal.puertocorazon.core.utils.CUBaseViewModel
import com.boreal.puertocorazon.core.utils.corefirestore.errorhandler.CUAuthenticationErrorEnum
import com.boreal.puertocorazon.core.usecase.AuthUseCase
import com.boreal.puertocorazon.login.usecase.LoginUseCase
import kotlinx.coroutines.flow.collect

class ALoginViewModel(
    private val getLoginUseCase:
    UseCase<LoginUseCase.Input, LoginUseCase.Output>,
    private val getAuthUseCase:
    UseCase<EmptyIn, AuthUseCase.Output>
) : CUBaseViewModel() {

    val loginData: LiveData<AFirestoreAuthResponse<AAuthLoginEmailModel, AAuthModel, CUAuthenticationErrorEnum>?>
        get() = _loginData
    private val _loginData =
        MutableLiveData<AFirestoreAuthResponse<AAuthLoginEmailModel, AAuthModel, CUAuthenticationErrorEnum>?>()

    val authUser: LiveData<Pair<AFirestoreStatusRequest, AAuthModel?>>
        get() = _authUser
    private val _authUser = MutableLiveData<Pair<AFirestoreStatusRequest, AAuthModel?>>(
        Pair(
            AFirestoreStatusRequest.NONE,
            null
        )
    )

    fun resetLoginData(){
        _loginData.value = null
    }

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

    fun getLocalUser() {
        executeFlow {
            _authUser.value = Pair(AFirestoreStatusRequest.LOADING, AAuthModel())
            getAuthUseCase.execute(EmptyIn).collect {
                _authUser.value = it.response
            }
        }
    }

}