package com.boreal.puertocorazon.adm.home.ui

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import com.boreal.commonutils.base.CUBaseFragment
import com.boreal.commonutils.extensions.setOnSingleClickListener
import com.boreal.commonutils.extensions.showToast
import com.boreal.commonutils.utils.GAdapter
import com.boreal.puertocorazon.adm.home.R
import com.boreal.puertocorazon.adm.home.databinding.PcAdmHomeFragmentBinding
import com.boreal.puertocorazon.core.domain.entity.AFirestoreStatusRequest
import com.boreal.puertocorazon.core.domain.entity.event.PCEventModel
import com.boreal.puertocorazon.core.utils.corefirestore.errorhandler.CUFirestoreErrorEnum
import com.boreal.puertocorazon.core.viewmodel.PCBaseViewModel
import com.boreal.puertocorazon.uisystem.databinding.PcHomeEventItemBinding
import com.boreal.puertocorazon.uisystem.databinding.PcHomeServiceItemBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PCAdmHomeFragment :
    CUBaseFragment<PcAdmHomeFragmentBinding>() {

    private val viewModelBase: PCBaseViewModel by sharedViewModel()
    val viewModel: PCHomeViewModel by viewModel()

    val adapterRecyclerAdmHomeEvent by lazy {
        GAdapter<PcHomeEventItemBinding, PCEventModel>(
            R.layout.pc_home_event_item,
            AsyncDifferConfig.Builder(object : DiffUtil.ItemCallback<PCEventModel>() {
                override fun areItemsTheSame(
                    oldItem: PCEventModel,
                    newItem: PCEventModel
                ) = oldItem == newItem

                override fun areContentsTheSame(
                    oldItem: PCEventModel,
                    newItem: PCEventModel
                ) = oldItem == newItem

            }).build(),
            holderCallback = { bindingElement, model, list, adapter, position ->
//                binding.customModel = model.userData
                bindingElement.apply {
                    txtTitleEvent.text = model.title
                    containerEventItem.setOnSingleClickListener {
                        findNavController().navigate(R.id.pc_show_event_graph)
                    }
                }
            }
        )
    }

    val adapterRecyclerHomeService by lazy {
        GAdapter<PcHomeServiceItemBinding, String>(
            R.layout.pc_home_service_item,
            AsyncDifferConfig.Builder(object : DiffUtil.ItemCallback<String>() {
                override fun areItemsTheSame(
                    oldItem: String,
                    newItem: String
                ) = oldItem == newItem

                override fun areContentsTheSame(
                    oldItem: String,
                    newItem: String
                ) = oldItem == newItem

            }).build(),
            holderCallback = { binding, model, list, adapter, position ->
                binding.apply {

                }
            }
        )
    }

    override fun initObservers() {

        viewModel.eventList.observe(viewLifecycleOwner) {
            it?.let {
                when (it.status) {
                    AFirestoreStatusRequest.LOADING -> {
//                        showProgress()
                        binding.recyclerAdmHomeEvents.setLoading(AFirestoreStatusRequest.LOADING.ordinal)
                    }
                    AFirestoreStatusRequest.SUCCESS, AFirestoreStatusRequest.FAILURE -> {
                        binding.recyclerAdmHomeEvents.setLoading(AFirestoreStatusRequest.SUCCESS.ordinal)
//                        hideProgressBarCustom()
                        it.failure?.let { errorResult ->
                            binding.recyclerAdmHomeEvents.setLoading(AFirestoreStatusRequest.FAILURE.ordinal)
                            if (errorResult == CUFirestoreErrorEnum.ERROR_PERMISSION_DENIED) {
                                viewModelBase.signOutUser()
                            }
                            showToast(errorResult.messageError)
                            return@observe
                        }
                        loadRecyclerEvent(it.response!!)

                    }
                }
            }
        }
    }


    override fun getLayout() = R.layout.pc_adm_home_fragment

    override fun initView() {
        initElements()
    }

}