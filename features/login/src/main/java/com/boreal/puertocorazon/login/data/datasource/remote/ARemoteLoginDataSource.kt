package com.boreal.puertocorazon.login.data.datasource.remote

import com.boreal.puertocorazon.core.domain.AFirestoreAuthResponse
import com.boreal.puertocorazon.core.domain.AFirestoreRepository
import com.boreal.puertocorazon.core.domain.AFirestoreStatusRequest
import com.boreal.puertocorazon.core.domain.entity.auth.AAuthLoginEmailModel
import com.boreal.puertocorazon.core.utils.corefirestore.errorhandler.CUAuthenticationErrorEnum
import com.boreal.puertocorazon.login.ui.login.data.datasource.GetLoginDataSource
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class ARemoteLoginDataSource : GetLoginDataSource, AFirestoreRepository() {
    override fun executeLogin(request: AAuthLoginEmailModel): Flow<AFirestoreAuthResponse<AAuthLoginEmailModel, Task<AuthResult>, CUAuthenticationErrorEnum>> =
        flow {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(request.email, request.token)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        coroutineScope.launch {
                            emit(
                                AFirestoreAuthResponse(
                                    authModel = request,
                                    response = it,
                                    status = AFirestoreStatusRequest.SUCCESS
                                )
                            )
                        }
                    } else {
                        coroutineScope.launch {
                            emit(
                                AFirestoreAuthResponse(
                                    authModel = request,
                                    response = null,
                                    failure = authenticationValidException(
                                        it.exception ?: java.lang.Exception("Error default")
                                    ),
                                    status = AFirestoreStatusRequest.FAILURE
                                )
                            )
                        }
                    }
                }
        }
}