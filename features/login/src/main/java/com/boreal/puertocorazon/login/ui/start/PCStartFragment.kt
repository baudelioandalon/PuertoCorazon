package com.boreal.puertocorazon.login.ui.start

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.boreal.commonutils.application.CUAppInit
import com.boreal.commonutils.base.CUBaseFragment
import com.boreal.commonutils.extensions.showToast
import com.boreal.puertocorazon.core.domain.entity.AFirestoreStatusRequest
import com.boreal.puertocorazon.core.domain.entity.auth.AAuthConvert
import com.boreal.puertocorazon.core.domain.entity.auth.AAuthModel
import com.boreal.puertocorazon.core.domain.entity.auth.PCUserType
import com.boreal.puertocorazon.core.viewmodel.PCMainViewModel
import com.boreal.puertocorazon.login.R
import com.boreal.puertocorazon.login.data.LoginDataSource
import com.boreal.puertocorazon.login.databinding.PcStartLoginFragmentBinding
import com.boreal.puertocorazon.login.viewmodel.ALoginViewModel
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import io.realm.Realm
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PCStartFragment :
    CUBaseFragment<PcStartLoginFragmentBinding>() {

    companion object {
        const val REQ_GOOGLE = 1000
    }

    private lateinit var auth: FirebaseAuth

    lateinit var oneTapClient: SignInClient
    val viewModel: ALoginViewModel by sharedViewModel()
    private val mainViewModel: PCMainViewModel by activityViewModels()

    override fun getLayout() = R.layout.pc_start_login_fragment

    override fun initDependency(savedInstanceState: Bundle?) {
        CUAppInit().init(requireActivity().application, requireContext())
        Realm.init(requireContext())
        Realm.deleteRealm(Realm.getDefaultConfiguration())
        auth = FirebaseAuth.getInstance()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQ_GOOGLE -> {
                try {
                    val credential = oneTapClient.getSignInCredentialFromIntent(data)
                    val idToken = credential.googleIdToken
                    val username = credential.id
                    val password = credential.password
                    when {
                        idToken != null -> {
                            // Got an ID token from Google. Use it to authenticate
                            // with Firebase.

                            val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                            auth.signInWithCredential(firebaseCredential)
                                .addOnCompleteListener(requireActivity()) { task ->
                                    if (task.isSuccessful) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("LOGIN_GOOGLE", "signInWithCredential:success")
                                        val user = auth.currentUser
                                        val authModel = AAuthConvert<AAuthModel>(AAuthModel::class).getDataType(FirebaseAuth.getInstance().getAccessToken(false).result.claims)
                                        auth.signOut()
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.e(
                                            "LOGIN_GOOGLE",
                                            "signInWithCredential:failure",
                                            task.exception
                                        )
                                    }
                                }
                            Log.d("LOGIN_GOOGLE", "Got ID token.")
                        }
                        password != null -> {
                            // Got a saved username and password. Use them to authenticate
                            // with your backend.
                            Log.d("LOGIN_GOOGLE", "Got password.")
                        }
                        else -> {
                            // Shouldn't happen.
                            Log.d("LOGIN_GOOGLE", "No ID token or password!")
                        }
                    }
                } catch (e: ApiException) {
                    e
                    // ...
                }
            }
        }
    }

    override fun initObservers() {

        if (FirebaseAuth.getInstance().currentUser != null) {
            viewModel.getLocalUser()
            viewModel.authUser.observe(viewLifecycleOwner) {
                it?.let { authInfo ->
                    when (authInfo.first) {
                        AFirestoreStatusRequest.LOADING -> {
                            showProgress()
                        }
                        AFirestoreStatusRequest.SUCCESS, AFirestoreStatusRequest.FAILURE -> {
                            viewModel.resetLoginData()
                            navigateToHome(authInfo.second)
                        }
                    }
                }
            }
        } else {
            mainViewModel.authUser.observe(viewLifecycleOwner) {
                if (it != null && it.userType != PCUserType.NONE.type) {
                    if (mainViewModel.allowExit) {
                        mainViewModel.allowExit = false
                        navigateToHome(it)
                    }
                }
            }
            viewModel.loginData.observe(viewLifecycleOwner) { result ->
                when (result?.status) {
                    AFirestoreStatusRequest.LOADING -> {
                        showProgress()
                    }
                    AFirestoreStatusRequest.SUCCESS -> {
                        hideProgressBarCustom()
                        result.response?.let {
                            if (it.email_verified) {
                                mainViewModel.setAuthUser(it)
                            }
                        }
                    }
                    AFirestoreStatusRequest.FAILURE -> {
                        hideProgressBarCustom()
                        result.failure?.let { errorData ->
                            showToast(
                                errorData.messageError
                            )
                        }
                    }
                }
            }
        }

    }

    private fun navigateToHome(userLocal: AAuthModel?) {
        if (userLocal != null) {
            when (userLocal.userType) {
                PCUserType.ADMINISTRATOR.type -> {
                    mainViewModel.allowExit = false
                    findNavController().navigate(R.id.action_PCStartFragment_to_pc_adm_home_graph)
                        .run {
                            hideProgressBarCustom()
                        }
                }
                PCUserType.CLIENT.type -> {
                    mainViewModel.allowExit = false
                    findNavController().navigate(R.id.action_PCStartFragment_to_pc_client_home_graph)
                        .run {
                            hideProgressBarCustom()
                        }
                }
                else -> {
                    mainViewModel.allowExit = true
                    showToast("Tipo de usuario ${userLocal.userType} no controlado")
                    hideProgressBarCustom()
                }
            }
        } else {
            mainViewModel.allowExit = true
            FirebaseAuth.getInstance().signOut()
            showToast("El usuario no se encontró")
            hideProgressBarCustom()
        }
    }

    override fun initView() {
        initElements()
        initAnimations()
    }

    override fun onPause() {
        super.onPause()
        viewModel.resetLoginData()
    }
}