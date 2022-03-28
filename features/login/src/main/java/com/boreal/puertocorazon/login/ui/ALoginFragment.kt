package com.boreal.puertocorazon.login.ui

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.boreal.commonutils.application.CUAppInit
import com.boreal.commonutils.base.CUBaseFragment
import com.boreal.commonutils.extensions.showToast
import com.boreal.puertocorazon.core.domain.entity.AFirestoreStatusRequest
import com.boreal.puertocorazon.core.domain.entity.auth.AAuthModel
import com.boreal.puertocorazon.core.domain.entity.auth.PCUserType
import com.boreal.puertocorazon.core.viewmodel.PCBaseViewModel
import com.boreal.puertocorazon.login.R
import com.boreal.puertocorazon.login.databinding.ALoginFragmentBinding
import com.google.firebase.auth.FirebaseAuth
import io.realm.Realm
import org.koin.androidx.viewmodel.ext.android.viewModel

class ALoginFragment :
    CUBaseFragment<ALoginFragmentBinding>() {

    val viewModel: ALoginViewModel by viewModel()
    private val viewModelBase: PCBaseViewModel by activityViewModels()

    override fun getLayout() = R.layout.a_login_fragment

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
                            showProgressBarCustom()
                        }
                        AFirestoreStatusRequest.SUCCESS, AFirestoreStatusRequest.FAILURE -> {
                            viewModel.resetLoginData()
                            navigateToHome(authInfo.second)
                        }
                    }
                }
            }
        } else {
            viewModelBase.authUser.observe(viewLifecycleOwner) {
                if (it != null && it.userType != PCUserType.NONE.type) {
                    if (viewModelBase.allowExit) {
                        viewModelBase.allowExit = false
                        navigateToHome(it)
                    }
                }
            }
            viewModel.loginData.observe(viewLifecycleOwner) { result ->
                when (result?.status) {
                    AFirestoreStatusRequest.LOADING -> {
                        showProgressBarCustom()
                    }
                    AFirestoreStatusRequest.SUCCESS -> {
                        hideProgressBarCustom()
                        result.response?.let {
                            if (it.email_verified) {
                                viewModelBase.setAuthUser(it)
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
                    viewModelBase.allowExit = false
                    findNavController().navigate(R.id.action_ALoginFragment_to_pc_adm_home_graph)
                        .run {
                            hideProgressBarCustom()
                        }
                }
                PCUserType.CLIENT.type -> {
                    viewModelBase.allowExit = false
                    findNavController().navigate(R.id.action_ALoginFragment_to_pc_client_home_graph)
                        .run {
                            hideProgressBarCustom()
                        }
                }
                else -> {
                    viewModelBase.allowExit = true
                    showToast("Tipo de usuario ${userLocal.userType} no controlado")
                    hideProgressBarCustom()
                }
            }
        } else {
            viewModelBase.allowExit = true
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