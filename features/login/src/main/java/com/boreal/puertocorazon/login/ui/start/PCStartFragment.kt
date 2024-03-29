package com.boreal.puertocorazon.login.ui.start

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.boreal.commonutils.application.CUAppInit
import com.boreal.commonutils.base.CUBaseFragment
import com.boreal.commonutils.extensions.hideView
import com.boreal.commonutils.extensions.showToast
import com.boreal.commonutils.extensions.showView
import com.boreal.puertocorazon.core.domain.entity.AFirestoreStatusRequest
import com.boreal.puertocorazon.core.domain.entity.auth.AAuthModel
import com.boreal.puertocorazon.core.domain.entity.auth.PCUserType
import com.boreal.puertocorazon.core.viewmodel.PCMainViewModel
import com.boreal.puertocorazon.login.R
import com.boreal.puertocorazon.login.databinding.PcStartLoginFragmentBinding
import com.boreal.puertocorazon.login.viewmodel.ALoginViewModel
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.firebase.auth.FirebaseAuth
import io.realm.Realm
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PCStartFragment :
    CUBaseFragment<PcStartLoginFragmentBinding>() {

    companion object {
        const val REQ_GOOGLE = 1000
    }

    lateinit var oneTapClient: SignInClient
    lateinit var signInRequest: BeginSignInRequest
    val viewModel: ALoginViewModel by sharedViewModel()
    val mainViewModel: PCMainViewModel by activityViewModels()

    override fun getLayout() = R.layout.pc_start_login_fragment

    override fun initDependency(savedInstanceState: Bundle?) {
        CUAppInit().init(requireActivity().application, requireContext())
        Realm.init(requireContext())
        oneTapClient = Identity.getSignInClient(requireActivity())
        signInRequest = BeginSignInRequest.builder()
            .setPasswordRequestOptions(
                BeginSignInRequest.PasswordRequestOptions.builder()
                    .setSupported(true)
                    .build()
            )
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(getString(R.string.your_web_client_id))
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .build()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQ_GOOGLE -> {
                try {
                    if (oneTapClient.getSignInCredentialFromIntent(data).googleIdToken != null) {
                        viewModel.requestGoogleLogin(oneTapClient.getSignInCredentialFromIntent(data))
                    } else {
                        showToast("Algo salio mal")
                    }
                } catch (e: ApiException) {
                    Log.e("GOOGLE_ERROR", e.message ?: "ERROR DEFAULT")
                    if (e.statusCode != CommonStatusCodes.CANCELED) {
                        showToast("Algo salio mal")
                    }
                }
            }
        }
    }

    override fun initObservers() {

        if (FirebaseAuth.getInstance().currentUser != null) {
            viewModel.getLocalUser()
        } else {
            if (mainViewModel.resetLogin) {
                mainViewModel.hideSplash {
                    mainViewModel.resetLogin = false
                    binding.loginSplash.hideView()
                    binding.containerLogin.showView()
                }
            }else{
                binding.loginSplash.hideView()
                binding.containerLogin.showView()
            }
        }
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

                    AFirestoreStatusRequest.NONE ->{}
                }
            }
        }
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

                AFirestoreStatusRequest.NONE -> {}
                null -> {}
            }
        }
    }

    private fun navigateToHome(userLocal: AAuthModel?) {
        if (userLocal != null) {
            when (userLocal.userType) {
                PCUserType.ADMINISTRATOR.type -> {
                    mainViewModel.allowExit = false
                    mainViewModel.logOut = false
                    mainViewModel.setAuthUser(userLocal)
                    findNavController().navigate(R.id.action_PCStartFragment_to_pc_adm_menu_graph)
                        .run {
                            hideProgressBarCustom()
                        }
                }
                PCUserType.CLIENT.type -> {
                    mainViewModel.allowExit = false
                    mainViewModel.logOut = false
                    mainViewModel.setAuthUser(userLocal)
                    findNavController().navigate(R.id.action_PCStartFragment_to_pc_client_menu_graph)
                        .run {
                            hideProgressBarCustom()
                        }
                }
                else -> {
                    mainViewModel.allowExit = true
                    mainViewModel.logOut = false
                    showToast("Tipo de usuario ${userLocal.userType} no controlado")
                    mainViewModel.signOutUser()
                    hideProgressBarCustom()
                }
            }
        } else {
            mainViewModel.allowExit = true
            mainViewModel.logOut = false
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