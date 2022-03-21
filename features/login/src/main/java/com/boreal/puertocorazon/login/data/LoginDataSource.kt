package com.boreal.puertocorazon.login.data

import com.boreal.puertocorazon.core.domain.entity.AFirestoreAuthResponse
import com.boreal.puertocorazon.core.domain.entity.AFirestoreRepository
import com.boreal.puertocorazon.core.domain.entity.AFirestoreStatusRequest
import com.boreal.puertocorazon.core.domain.entity.auth.AAuthLoginEmailModel
import com.boreal.puertocorazon.core.utils.corefirestore.errorhandler.CUAuthenticationErrorEnum
import com.boreal.puertocorazon.core.utils.log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class LoginDataSource : AFirestoreRepository() {

    companion object {
        suspend fun getLogin(request: AAuthLoginEmailModel): AFirestoreAuthResponse<AAuthLoginEmailModel, AuthResult, CUAuthenticationErrorEnum> {
            val resultData = FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(request.email, request.token).await()
            return if (resultData.user != null) {
                "CORRECTO".log("AUTHENTICATION")
                AFirestoreAuthResponse(
                    authModel = request,
                    response = resultData,
                    status = AFirestoreStatusRequest.SUCCESS
                )
            } else {
                "INCORRECTO".log("AUTHENTICATION")
                AFirestoreAuthResponse(
                    authModel = request,
                    response = null,
                    failure = authenticationValidException(
                        Exception("Error default")
                    ),
                    status = AFirestoreStatusRequest.FAILURE
                )
            }
        }
    }
}


suspend fun <T> Task<T>.await(): T {
    // fast path
    if (isComplete) {
        val e = exception
        return if (e == null) {
            if (isCanceled) {
                throw CancellationException(
                    "Task $this was cancelled normally."
                )
            } else {
                result
            }
        } else {
            throw e
        }
    }

    return suspendCancellableCoroutine { cont ->
        addOnCompleteListener {
            val e = exception
            if (e == null) {
                if (isCanceled) cont.cancel() else cont.resume(it.result)
            } else {
                cont.resumeWithException(e)
            }
        }
    }
}
