package com.boreal.puertocorazon.login.data

import com.boreal.puertocorazon.core.domain.entity.AFirestoreAuthResponse
import com.boreal.puertocorazon.core.domain.entity.AFirestoreRepository
import com.boreal.puertocorazon.core.domain.entity.AFirestoreStatusRequest
import com.boreal.puertocorazon.core.domain.entity.auth.AAuthConvert
import com.boreal.puertocorazon.core.domain.entity.auth.AAuthLoginEmailModel
import com.boreal.puertocorazon.core.domain.entity.auth.AAuthModel
import com.boreal.puertocorazon.core.domain.entity.auth.PCTypeSession
import com.boreal.puertocorazon.core.utils.coreauthentication.await
import com.boreal.puertocorazon.core.utils.corefirestore.errorhandler.CUAuthenticationErrorEnum
import com.boreal.puertocorazon.core.utils.realm.saveLocal

class LoginDataSource {
    companion object : AFirestoreRepository() {
        suspend fun getLogin(
            request: AAuthLoginEmailModel,
            verifiedEmailRequired: Boolean = true,
            saveAuthLocal: Boolean = true
        ): AFirestoreAuthResponse<AAuthLoginEmailModel, AAuthModel, CUAuthenticationErrorEnum> {
            return with(
                firebaseAuth.signInWithEmailAndPassword(request.email, request.token).await()
            ) {
                try {
                    val authModel = AAuthConvert<AAuthModel>(AAuthModel::class)
                        .getDataType(firebaseAuth.getAccessToken(false).result.claims)
                    if (authModel.picture.contains("googleusercontent")) {
                        authModel.sign_in_provider = PCTypeSession.GOOGLE.name
                    }
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
    }
}