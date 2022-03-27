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
import com.boreal.puertocorazon.core.utils.realm.getRealmObject
import com.boreal.puertocorazon.core.viewmodel.PCBaseViewModel
import com.boreal.puertocorazon.login.R
import com.boreal.puertocorazon.login.databinding.ALoginFragmentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import io.realm.Realm
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ALoginFragment :
    CUBaseFragment<ALoginFragmentBinding>() {

    val viewModel: ALoginViewModel by sharedViewModel()
    private val viewModelBase: PCBaseViewModel by activityViewModels()

    override fun getLayout() = R.layout.a_login_fragment

    override fun initDependency(savedInstanceState: Bundle?) {
        CUAppInit().init(requireActivity().application, requireContext())
        Realm.init(requireContext())
    }

    override fun initObservers() {

        if (FirebaseAuth.getInstance().currentUser != null) {
            viewModelBase.allowExit = false
            getRealmObject<AAuthModel>().run {
                navigateToHome(
                    if (this != null) {
                        val resultFormatted = Gson().fromJson(
                            "{\"" + Gson().toJson(toString()).replace(":", "\":\"")
                                .replace("}", "\"}").substringAfter("proxy[")
                                .replace("]\"", "").replace("{", "").replace(",", ",\"")
                                .replace("}", "").replace(
                                    "\"https\":\"//securetoken.google.com/puertocorazonapp\"",
                                    "\"https://securetoken.google.com/puertocorazonapp\""
                                ).replace(",\"firebase\":\"FirebaseModel\"", "") + "}",
                            AAuthModel::class.java
                        )
                        resultFormatted
                    } else {
                        this
                    }
                )
            }

        } else {
            viewModelBase.authUser.observe(viewLifecycleOwner) {
                if (it != null) {
                    if (viewModelBase.allowExit) {
                        viewModelBase.allowExit = false
                        navigateToHome(it)
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

    private fun navigateToHome(userLocal: AAuthModel?) {

        if (userLocal != null) {
            when (userLocal.userType) {
                PCUserType.ADMINISTRATOR.type -> {
                    findNavController().navigate(R.id.action_ALoginFragment_to_pc_adm_home_graph)
                }
                PCUserType.CLIENT.type -> {
                    findNavController().navigate(R.id.action_ALoginFragment_to_pc_client_home_graph)
                }
                else -> {
                    viewModelBase.allowExit = true
                    showToast("Tipo de usuario ${userLocal.userType} no controlado")
                }
            }
        } else {
            viewModelBase.allowExit = true
            FirebaseAuth.getInstance().signOut()
            showToast("El usuario no se encontró")
        }
    }

    override fun initView() {
        initElements()
    }


}