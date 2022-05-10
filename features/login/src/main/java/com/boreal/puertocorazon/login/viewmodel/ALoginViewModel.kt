package com.boreal.puertocorazon.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.boreal.puertocorazon.core.domain.entity.AFirestoreAuthResponse
import com.boreal.puertocorazon.core.domain.entity.AFirestoreStatusRequest
import com.boreal.puertocorazon.core.domain.entity.auth.AAuthLoginEmailModel
import com.boreal.puertocorazon.core.domain.entity.auth.AAuthModel
import com.boreal.puertocorazon.core.domain.entity.auth.PCTypeSession
import com.boreal.puertocorazon.core.usecase.AuthUseCase
import com.boreal.puertocorazon.core.usecase.EmptyIn
import com.boreal.puertocorazon.core.usecase.UseCase
import com.boreal.puertocorazon.core.utils.CUBaseViewModel
import com.boreal.puertocorazon.core.utils.corefirestore.errorhandler.CUAuthenticationErrorEnum
import com.boreal.puertocorazon.login.usecase.LoginGoogleUseCase
import com.boreal.puertocorazon.login.usecase.LoginNormalUseCase
import com.google.android.gms.auth.api.identity.SignInCredential
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect

class ALoginViewModel(
    private val getLoginNormalUseCase:
    UseCase<LoginNormalUseCase.Input, LoginNormalUseCase.Output>,
    private val getLoginGoogleUseCase:
    UseCase<LoginGoogleUseCase.Input, LoginGoogleUseCase.Output>,
    private val getAuthUseCase:
    UseCase<EmptyIn, AuthUseCase.Output>
) : CUBaseViewModel() {

    val loginData: LiveData<AFirestoreAuthResponse<AAuthLoginEmailModel?, AAuthModel?, CUAuthenticationErrorEnum>?>
        get() = _loginData
    private val _loginData =
        MutableLiveData<AFirestoreAuthResponse<AAuthLoginEmailModel?, AAuthModel?, CUAuthenticationErrorEnum>?>()

    val authUser: LiveData<Pair<AFirestoreStatusRequest, AAuthModel?>>
        get() = _authUser
    private val _authUser = MutableLiveData<Pair<AFirestoreStatusRequest, AAuthModel?>>(
        Pair(
            AFirestoreStatusRequest.NONE,
            null
        )
    )

    private var typeSession: PCTypeSession = PCTypeSession.NORMAL

    fun setTypeSession(typeAuthenticator: PCTypeSession) {
        typeSession = typeAuthenticator
    }

    fun resetLoginData() {
        _loginData.value = null
    }

    fun requestNormalLogin(request: AAuthLoginEmailModel) {
        executeFlow {
            _loginData.postValue(
                AFirestoreAuthResponse(
                    status = AFirestoreStatusRequest.LOADING,
                    authModel = request
                )
            )
            getLoginNormalUseCase.execute(LoginNormalUseCase.Input(request)).collect {
                _loginData.postValue(it.response)
            }
        }
    }

    fun requestGoogleLogin(request: SignInCredential) {
        executeFlow {
            _loginData.postValue(
                AFirestoreAuthResponse(
                    status = AFirestoreStatusRequest.LOADING,
                    authModel = AAuthLoginEmailModel(request.id, request.googleIdToken ?: "")
                )
            )
            getLoginGoogleUseCase.execute(LoginGoogleUseCase.Input(request)).collect {
                _loginData.postValue(it.response)
            }
        }
    }

    fun getLocalUser() {
        executeFlow {
            _authUser.value = Pair(AFirestoreStatusRequest.LOADING, AAuthModel())
            getAuthUseCase.execute(EmptyIn).catch { cause: Throwable ->
                cause
            }.collect {
                _authUser.value = it.response
            }
        }
    }

}