package com.boreal.puertocorazon.login.ui

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.boreal.commonutils.application.CUAppInit
import com.boreal.commonutils.base.CUBaseFragment
import com.boreal.commonutils.extensions.showToast
import com.boreal.puertocorazon.core.domain.entity.AFirestoreStatusRequest
import com.boreal.puertocorazon.core.viewmodel.PCBaseViewModel
import com.boreal.puertocorazon.login.R
import com.boreal.puertocorazon.login.databinding.ALoginFragmentBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ALoginFragment :
    CUBaseFragment<ALoginFragmentBinding>() {

    val viewModel: ALoginViewModel by sharedViewModel()
    private val viewModelBase: PCBaseViewModel by activityViewModels()

    override fun getLayout() = R.layout.a_login_fragment

    override fun initDependency(savedInstanceState: Bundle?) {
        CUAppInit().init(requireActivity().application, requireContext())
    }

    override fun initObservers() {

        if (false) {
            viewModelBase.allowExit = false
            findNavController().navigate(R.id.action_ALoginFragment_to_pc_home_client_graph)
        } else {
            viewModelBase.authUser.observe(viewLifecycleOwner) {
                if (it != null) {
                    if (viewModelBase.allowExit) {
                        viewModelBase.allowExit = false
                        findNavController().navigate(R.id.action_ALoginFragment_to_pc_home_client_graph)
                    }
                }
            }
            viewModel.loginData.observe(viewLifecycleOwner) { result ->
                when (result.status) {
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

    override fun initView() {
        initElements()
    }


}