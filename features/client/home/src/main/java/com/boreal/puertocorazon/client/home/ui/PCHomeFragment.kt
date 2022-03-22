package com.boreal.puertocorazon.client.home.ui

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import com.boreal.commonutils.base.CUBaseFragment
import com.boreal.commonutils.extensions.setOnSingleClickListener
import com.boreal.commonutils.utils.GAdapter
import com.boreal.puertocorazon.client.home.R
import com.boreal.puertocorazon.client.home.databinding.PcHomeEventItemBinding
import com.boreal.puertocorazon.client.home.databinding.PcHomeFragmentBinding
import com.boreal.puertocorazon.client.home.databinding.PcHomeServiceItemBinding
import com.boreal.puertocorazon.core.viewmodel.PCBaseViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PCHomeFragment :
    CUBaseFragment<PcHomeFragmentBinding>() {

    private val viewModelBase: PCBaseViewModel by sharedViewModel()

    val adapterRecyclerHomeEvent by lazy {
        GAdapter<PcHomeEventItemBinding, String>(
            R.layout.pc_home_event_item,
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
            holderCallback = { binding, model, list, adapter ->
//                binding.customModel = model.userData
                binding.apply {
                    containerEventItem.setOnSingleClickListener {
                        findNavController().navigate(R.id.action_PCHomeFragment_to_pc_client_event_graph)
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
            holderCallback = { binding, model, list, adapter ->
                binding.apply {

                }
            }
        )
    }

    override fun getLayout() = R.layout.pc_home_fragment

    override fun initView() {
        initElements()
    }

}