package com.boreal.puertocorazon.addevent.ui.packages

import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import com.boreal.commonutils.base.CUBaseFragment
import com.boreal.commonutils.extensions.hideView
import com.boreal.commonutils.extensions.showView
import com.boreal.commonutils.utils.GAdapter
import com.boreal.puertocorazon.addevent.R
import com.boreal.puertocorazon.addevent.databinding.PcPackagesAddEventFragmentBinding
import com.boreal.puertocorazon.addevent.viewmodel.AddEventViewModel
import com.boreal.puertocorazon.core.domain.entity.event.PCPackageToUploadModel
import com.boreal.puertocorazon.uisystem.databinding.PcPackageToUploadItemBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PCPackagesAddEventFragment : CUBaseFragment<PcPackagesAddEventFragmentBinding>() {

    val viewModel: AddEventViewModel by sharedViewModel()

    val adapterRecyclerPackages by lazy {
        GAdapter<PcPackageToUploadItemBinding, PCPackageToUploadModel>(
            R.layout.pc_package_to_upload_item,
            AsyncDifferConfig.Builder(object : DiffUtil.ItemCallback<PCPackageToUploadModel>() {
                override fun areItemsTheSame(
                    oldItem: PCPackageToUploadModel,
                    newItem: PCPackageToUploadModel
                ) = oldItem == newItem

                override fun areContentsTheSame(
                    oldItem: PCPackageToUploadModel,
                    newItem: PCPackageToUploadModel
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