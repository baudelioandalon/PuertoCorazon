package com.boreal.puertocorazon.generic.ui.fragments.home

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import com.boreal.commonutils.base.CUBaseFragment
import com.boreal.commonutils.extensions.setOnSingleClickListener
import com.boreal.commonutils.utils.GAdapter
import com.boreal.puertocorazon.R
import com.boreal.puertocorazon.databinding.PcHomeEventItemBinding
import com.boreal.puertocorazon.databinding.PcHomeFragmentBinding
import com.boreal.puertocorazon.databinding.PcHomeServiceItemBinding

class PCHomeFragment :
    CUBaseFragment<PcHomeFragmentBinding, PCHomeViewModel>(PCHomeViewModel::class) {

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
                        findNavController().navigate(R.id.action_PCHomeFragment_to_PCShowEventFragment)

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
//                binding.customModel = model.userData
                binding.apply {

                }
            }
        )
    }

    override fun getLayout() = R.layout.pc_home_fragment

    override fun initDependency(savedInstanceState: Bundle?) {
    }

    override fun initObservers() {
    }

    override fun initView() {
        initElements()
    }
}