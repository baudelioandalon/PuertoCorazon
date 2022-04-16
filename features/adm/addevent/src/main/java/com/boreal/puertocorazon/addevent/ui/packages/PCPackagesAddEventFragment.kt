package com.boreal.puertocorazon.addevent.ui.packages

import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import com.boreal.commonutils.base.CUBaseFragment
import com.boreal.commonutils.extensions.hideView
import com.boreal.commonutils.extensions.showView
import com.boreal.commonutils.utils.GAdapter
import com.boreal.puertocorazon.addevent.R
import com.boreal.puertocorazon.addevent.databinding.PcPackagesAddEventFragmentBinding
import com.boreal.puertocorazon.core.domain.entity.event.PCPackageModel
import com.boreal.puertocorazon.uisystem.databinding.PcPackageItemBinding

class PCPackagesAddEventFragment : CUBaseFragment<PcPackagesAddEventFragmentBinding>() {

    val adapterRecyclerPackages by lazy {
        GAdapter<PcPackageItemBinding, PCPackageModel>(
            R.layout.pc_package_item,
            AsyncDifferConfig.Builder(object : DiffUtil.ItemCallback<PCPackageModel>() {
                override fun areItemsTheSame(
                    oldItem: PCPackageModel,
                    newItem: PCPackageModel
                ) = oldItem == newItem

                override fun areContentsTheSame(
                    oldItem: PCPackageModel,
                    newItem: PCPackageModel
                ) = oldItem == newItem

            }).build(),
            holderCallback = { bindingElement, model, list, adapter, position ->
                bindingElement.apply {
                    if (model.empty) {
                        containerPackageEmpty.showView()
                        containerPackageFilled.hideView()
                    } else {
                        containerPackageEmpty.hideView()
                        containerPackageFilled.showView()
                    }
                }
            }
        )
    }

    override fun getLayout() = R.layout.pc_packages_add_event_fragment

    override fun initView() {
        initElements()
    }
}