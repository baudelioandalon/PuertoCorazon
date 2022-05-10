package com.boreal.puertocorazon.client.home.ui

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import com.boreal.commonutils.base.CUBaseFragment
import com.boreal.commonutils.extensions.onClick
import com.boreal.commonutils.extensions.showToast
import com.boreal.commonutils.utils.GAdapter
import com.boreal.puertocorazon.client.home.R
import com.boreal.puertocorazon.client.home.databinding.PcClientHomeFragmentBinding
import com.boreal.puertocorazon.core.domain.entity.AFirestoreStatusRequest
import com.boreal.puertocorazon.core.domain.entity.event.PCEventModel
import com.boreal.puertocorazon.core.utils.corefirestore.errorhandler.CUFirestoreErrorEnum
import com.boreal.puertocorazon.core.viewmodel.PCMainViewModel
import com.boreal.puertocorazon.uisystem.databinding.PcHomeEventItemBinding
import com.boreal.puertocorazon.uisystem.databinding.PcHomeServiceItemBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PCClientHomeFragment :
    CUBaseFragment<PcClientHomeFragmentBinding>() {

    val mainViewModel: PCMainViewModel by sharedViewModel()

    val adapterRecyclerHomeEvent by lazy {
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
                    homeImg = model.homeImageUrl
                    containerEventItem.onClick {
                        mainViewModel.setEventSelected(model)
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

        mainViewModel.eventSelected.observe(viewLifecycleOwner) {
            it?.let {
                if (it.idEvent != "NONE") {
                    findNavController().navigate(R.id.pc_show_event_graph)
                }
            }
        }

        mainViewModel.eventList.observe(viewLifecycleOwner) {
            it?.let {
                when (it.status) {
                    AFirestoreStatusRequest.LOADING -> {
//                        showProgress()
                        binding.recyclerClientHomeEvents.setLoading(AFirestoreStatusRequest.LOADING.ordinal)
                    }
                    AFirestoreStatusRequest.SUCCESS, AFirestoreStatusRequest.FAILURE -> {
                        binding.recyclerClientHomeEvents.setLoading(it.status.ordinal)
//                        hideProgressBarCustom()
                        it.failure?.let { errorResult ->
                            if (errorResult == CUFirestoreErrorEnum.ERROR_PERMISSION_DENIED) {
                                mainViewModel.signOutUser()
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

    override fun getLayout() = R.layout.pc_client_home_fragment

    override fun initView() {
        initElements()
    }

}