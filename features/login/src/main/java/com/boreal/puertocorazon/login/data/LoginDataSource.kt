package com.boreal.puertocorazon.login.data

import android.util.Log
import com.boreal.puertocorazon.core.domain.entity.AFirestoreAuthResponse
import com.boreal.puertocorazon.core.domain.entity.AFirestoreRepository
import com.boreal.puertocorazon.core.domain.entity.AFirestoreStatusRequest
import com.boreal.puertocorazon.core.domain.entity.auth.*
import com.boreal.puertocorazon.core.utils.coreauthentication.await
import com.boreal.puertocorazon.core.utils.coreauthentication.awaitTask
import com.boreal.puertocorazon.core.utils.corefirestore.errorhandler.CUAuthenticationErrorEnum
import com.boreal.puertocorazon.core.utils.realm.saveLocal
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider

class LoginDataSource {
    companion object : AFirestoreRepository() {
        suspend fun getNormalLogin(
            request: AAuthLoginEmailModel,
            verifiedEmailRequired: Boolean = true,
            saveAuthLocal: Boolean = true
        ): AFirestoreAuthResponse<AAuthLoginEmailModel?, AAuthModel?, CUAuthenticationErrorEnum> {
            return with(
                firebaseAuth.signInWithEmailAndPassword(request.email, request.token).await()
            ) {
                try {
                    val authModel = AAuthConvert<AAuthModel>(AAuthModel::class)
                        .getDataType(firebaseAuth.getAccessToken(false).result.claims)
                    if (user != null) {
                        if (verifiedEmailRequired) {
                            if (authModel.email_verified) {
                                if (saveAuthLocal) authModel.saveLocal()
                                AFirestoreAuthResponse(
                                    authModel = request,
                                    response = authModel,
                                    status = AFirestoreStatusRequest.SUCCESS
                                )
                            } else {
                                user!!.sendEmailVerification()
                                firebaseAuth.signOut()
                                AFirestoreAuthResponse(
                                    authModel = request,
                                    response = null,
                                    failure = CUAuthenticationErrorEnum.ERROR_NOT_VERIFIED_EMAIL,
                                    status = AFirestoreStatusRequest.FAILURE
                                )
                            }

                        } else {
                            AFirestoreAuthResponse(
                                authModel = request,
                                response = authModel,
                                status = AFirestoreStatusRequest.SUCCESS
                            )
                        }
                    } else {
                        AFirestoreAuthResponse(
                            authModel = request,
                            response = null,
                            failure = CUAuthenticationErrorEnum.ERROR_USER_NOT_LOGGED_IN,
                            status = AFirestoreStatusRequest.FAILURE
                        )
                    }
                } catch (exception: Exception) {
                    AFirestoreAuthResponse(
                        authModel = request,
                        response = null,
                        failure = authenticationValidException(exception),
                        status = AFirestoreStatusRequest.FAILURE
                    )
                }
            }
        }

        suspend fun getGoogleLogin(
            signInCredential: SignInCredential,
            verifiedEmailRequired: Boolean = true,
            saveAuthLocal: Boolean = true
        ): AFirestoreAuthResponse<AAuthLoginEmailModel?, AAuthModel?, CUAuthenticationErrorEnum> {
            return with(
                firebaseAuth.signInWithCredential(
                    GoogleAuthProvider.getCredential(
                        signInCredential.googleIdToken,
                        null
                    )
                ).awaitTask()
            ) {
                try {
                    if (isSuccessful) {
                        val authModel = AAuthConvert<AAuthModel>(AAuthModel::class)
                            .getDataType(firebaseAuth.getAccessToken(false).result.claims)
                        if (authModel.picture.contains("googleusercontent")) {
                            authModel.sign_in_provider = PCTypeSession.GOOGLE.name
                            authModel.userType = PCUserType.CLIENT.type
                        }
                        if (result.user != null) {
                            if (verifiedEmailRequired) {
                                if (authModel.email_verified) {
                                    if (saveAuthLocal) authModel.saveLocal()
                                    AFirestoreAuthResponse(
                                        authModel = AAuthLoginEmailModel(
                                            authModel.email,
                                            signInCredential.googleIdToken ?: ""
                                        ),
                                        response = authModel,
                                        failure = null,
                                        status = AFirestoreStatusRequest.SUCCESS
                                    )
                                } else {
                                    result.user!!.sendEmailVerification()
                                    firebaseAuth.signOut()
                                    AFirestoreAuthResponse(
                                        authModel = null,
                                        response = null,
                                        failure = CUAuthenticationErrorEnum.ERROR_NOT_VERIFIED_EMAIL,
                                        status = AFirestoreStatusRequest.FAILURE
                                    )
                                }
                            } else {
                                AFirestoreAuthResponse(
                                    authModel = AAuthLoginEmailModel(
                                        authModel.email,
                                        signInCredential.googleIdToken ?: ""
                                    ),
                                    response = authModel,
                                    status = AFirestoreStatusRequest.SUCCESS
                                )
                            }
                        } else {
                            AFirestoreAuthResponse(
                                authModel = null,
                                response = null,
                                failure = CUAuthenticationErrorEnum.ERROR_USER_NOT_LOGGED_IN,
                                status = AFirestoreStatusRequest.FAILURE
                            )
                        }
                    } else {
                        Log.e(
                            "LOGIN_GOOGLE",
                            "signInWithCredential:failure",
                            exception
                        )
                        AFirestoreAuthResponse(
                            authModel = null,
                            response = null,
                            failure = authenticationValidException(
                                exception ?: java.lang.Exception(
                                    "${CUAuthenticationErrorEnum.ERROR_DEFAULT}"
                                )
                            ),
                            status = AFirestoreStatusRequest.FAILURE
                        )
                    }
                } catch (e: ApiException) {
                    AFirestoreAuthResponse(
                        authModel = null,
                        response = null,
                        failure = authenticationValidException(
                            exception
                                ?: java.lang.Exception("${CUAuthenticationErrorEnum.ERROR_DEFAULT}")
                        ),
                        status = AFirestoreStatusRequest.FAILURE
                    )
                }
            }
        }
    }
}