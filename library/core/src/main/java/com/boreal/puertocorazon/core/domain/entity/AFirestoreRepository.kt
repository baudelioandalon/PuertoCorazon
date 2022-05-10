package com.boreal.puertocorazon.core.domain.entity

import com.boreal.puertocorazon.core.utils.corefirestore.errorhandler.CUAuthenticationErrorEnum
import com.boreal.puertocorazon.core.utils.corefirestore.errorhandler.CUFirestoreErrorEnum
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import java.lang.Exception

abstract class AFirestoreRepository {
    protected val coroutineScope: CoroutineScope = MainScope()
    protected val firestoreInstance: FirebaseFirestore by lazy {
        Firebase.firestore
    }
    protected val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    companion object {
        fun errorResponse(causeThrowable: Throwable) =
            if (causeThrowable is FirebaseFirestoreException) {
                errorResponse(exception = causeThrowable)
            } else {
                CUFirestoreErrorEnum.ERROR_UNAVAILABLE
            }


        fun errorResponse(exception: FirebaseFirestoreException): CUFirestoreErrorEnum {
            return when (exception.code) {
                FirebaseFirestoreException.Code.UNAVAILABLE -> {
                    CUFirestoreErrorEnum.ERROR_UNAVAILABLE
                }
                FirebaseFirestoreException.Code.INVALID_ARGUMENT -> {
                    CUFirestoreErrorEnum.ERROR_UNAVAILABLE
                }
                FirebaseFirestoreException.Code.ABORTED -> {
                    CUFirestoreErrorEnum.ERROR_UNAVAILABLE
                }
                FirebaseFirestoreException.Code.ALREADY_EXISTS -> {
                    CUFirestoreErrorEnum.ERROR_UNAVAILABLE
                }
                FirebaseFirestoreException.Code.CANCELLED -> {
                    CUFirestoreErrorEnum.ERROR_UNAVAILABLE
                }
                FirebaseFirestoreException.Code.DATA_LOSS -> {
                    CUFirestoreErrorEnum.ERROR_UNAVAILABLE
                }
                FirebaseFirestoreException.Code.DEADLINE_EXCEEDED -> {
                    CUFirestoreErrorEnum.ERROR_UNAVAILABLE
                }
                FirebaseFirestoreException.Code.FAILED_PRECONDITION -> {
                    CUFirestoreErrorEnum.ERROR_UNAVAILABLE
                }
                FirebaseFirestoreException.Code.INTERNAL -> {
                    CUFirestoreErrorEnum.ERROR_UNAVAILABLE
                }
                FirebaseFirestoreException.Code.NOT_FOUND -> {
                    CUFirestoreErrorEnum.ERROR_NOT_FOUND
                }
                FirebaseFirestoreException.Code.OK -> {
                    CUFirestoreErrorEnum.ERROR_UNAVAILABLE
                }
                FirebaseFirestoreException.Code.OUT_OF_RANGE -> {
                    CUFirestoreErrorEnum.ERROR_UNAVAILABLE
                }
                FirebaseFirestoreException.Code.PERMISSION_DENIED -> {
                    CUFirestoreErrorEnum.ERROR_PERMISSION_DENIED
                }
                FirebaseFirestoreException.Code.RESOURCE_EXHAUSTED -> {
                    CUFirestoreErrorEnum.ERROR_UNAVAILABLE
                }
                FirebaseFirestoreException.Code.UNAUTHENTICATED -> {
                    CUFirestoreErrorEnum.ERROR_UNAVAILABLE
                }
                FirebaseFirestoreException.Code.UNIMPLEMENTED -> {
                    CUFirestoreErrorEnum.ERROR_UNAVAILABLE
                }
                FirebaseFirestoreException.Code.UNKNOWN -> {
                    CUFirestoreErrorEnum.ERROR_UNAVAILABLE
                }
            }
        }

        fun validationError(errorReceived: String): CUFirestoreErrorEnum {
            return when {
                errorReceived.contains(CUFirestoreErrorEnum.ERROR_INVALID_PATH.defaultError) -> {
                    CUFirestoreErrorEnum.ERROR_INVALID_PATH
                }
                errorReceived.contains(CUFirestoreErrorEnum.ERROR_INVALID_FIELD_PATH.defaultError) -> {
                    CUFirestoreErrorEnum.ERROR_INVALID_FIELD_PATH
                }
                errorReceived.contains(CUFirestoreErrorEnum.ERROR_NOT_FOUND.defaultError) -> {
                    CUFirestoreErrorEnum.ERROR_NOT_FOUND
                }
                errorReceived.contains(CUFirestoreErrorEnum.ERROR_DESERIALIZE_OBJECT.defaultError) -> {
                    CUFirestoreErrorEnum.ERROR_DESERIALIZE_OBJECT
                }
                else -> {
                    CUFirestoreErrorEnum.ERROR_DEFAULT
                }
            }
        }

        fun authenticationValidException(exception: Exception): CUAuthenticationErrorEnum {
            return when (exception) {
                is FirebaseAuthInvalidUserException -> {
                    CUAuthenticationErrorEnum.ERROR_USER_NOT_FOUND
                }
                is FirebaseAuthInvalidCredentialsException -> {
                    CUAuthenticationErrorEnum.ERROR_INVALID_PASSWORD
                }
                else -> {
                    CUAuthenticationErrorEnum.ERROR_DEFAULT
                }
            }
        }
    }


}