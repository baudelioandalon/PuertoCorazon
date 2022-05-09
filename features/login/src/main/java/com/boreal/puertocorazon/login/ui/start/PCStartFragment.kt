package com.boreal.puertocorazon.login.ui.start

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.boreal.commonutils.application.CUAppInit
import com.boreal.commonutils.base.CUBaseFragment
import com.boreal.commonutils.extensions.showToast
import com.boreal.puertocorazon.core.domain.entity.AFirestoreStatusRequest
import com.boreal.puertocorazon.core.domain.entity.auth.AAuthModel
import com.boreal.puertocorazon.core.domain.entity.auth.PCUserType
import com.boreal.puertocorazon.core.viewmodel.PCMainViewModel
import com.boreal.puertocorazon.login.R
import com.boreal.puertocorazon.login.databinding.PcStartLoginFragmentBinding
import com.boreal.puertocorazon.login.viewmodel.ALoginViewModel
import com.google.firebase.auth.FirebaseAuth
import io.realm.Realm
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PCStartFragment :
    CUBaseFragment<PcStartLoginFragmentBinding>() {

    val viewModel: ALoginViewModel by sharedViewModel()
    private val mainViewModel: PCMainViewModel by activityViewModels()

    override fun getLayout() = R.layout.pc_start_login_fragment

    override fun initDependency(savedInstanceState: Bundle?) {
        CUAppInit().init(requireActivity().application, requireContext())
        Realm.init(requireContext())
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
    }

    override fun onPause() {
        super.onPause()
        viewModel.resetLoginData()
    }
}