package com.boreal.puertocorazon.login.ui

import android.os.Bundle
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.boreal.commonutils.application.CUAppInit
import com.boreal.commonutils.base.CUBaseFragment
import com.boreal.commonutils.extensions.showToast
import com.boreal.puertocorazon.login.R
import com.google.firebase.auth.FirebaseAuth

class ALoginFragment :
    CUBaseFragment<ALoginFragmentBinding>() {

    val viewModel: ALoginViewModel by viewModel()
    private val viewModelBase: PCBaseViewModel by activityViewModels()

    override fun getLayout() = R.layout.a_login_fragment

    override fun initDependency(savedInstanceState: Bundle?) {
        CUAppInit().init(requireActivity().application, requireContext())
    }

    override fun initObservers() {

        if (FirebaseAuth.getInstance().currentUser != null) {
            viewModelBase.allowExit = false
            findNavController().navigate(R.id.action_ALoginFragment_to_ABaseFragment)
        } else {
            viewModelBase.authUser.observe(viewLifecycleOwner) {
                if (it != null) {
                    if (viewModelBase.allowExit) {
                        viewModelBase.allowExit = false
                        findNavController().navigate(R.id.action_ALoginFragment_to_ABaseFragment)
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
                            AAuthConvert<AAuthModel>(AAuthModel::class)
                                .getDataType(
                                    FirebaseAuth.getInstance().getAccessToken(false).result!!.claims
                                )
                                .runCatching {
                                    this
                                }.run {
                                    if (isSuccess) {
                                        getOrNull()?.let { response ->
                                            if (response.email_verified) {
                                                viewModelBase.setAuthUser(response)
                                            } else {
                                                FirebaseAuth.getInstance().currentUser!!.sendEmailVerification()
                                                    .addOnCompleteListener { task ->
                                                        FirebaseAuth.getInstance().signOut()
                                                        if (task.isSuccessful) {
                                                            showToast(
                                                                "Revisa tu bandeja de entrada para que valides tu correo",
                                                                Toast.LENGTH_LONG
                                                            )
                                                        } else {
                                                            showToast("Algo salio mal, por favor, vuelve a intentarlo")
                                                        }
                                                    }
                                            }
                                        }
                                    } else {
                                        showToast("Algo salio mal, por favor, vuelve a intentarlo")
                                    }
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