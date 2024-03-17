package com.boreal.puertocorazon.addevent.ui.packages

import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import com.boreal.commonutils.base.CUBaseFragment
import com.boreal.commonutils.extensions.*
import com.boreal.commonutils.utils.GAdapter
import com.boreal.puertocorazon.addevent.R
import com.boreal.puertocorazon.uisystem.R as uiR
import com.boreal.puertocorazon.addevent.databinding.PcPackagesAddEventFragmentBinding
import com.boreal.puertocorazon.addevent.ui.packages.addpackage.PCAddPackage
import com.boreal.puertocorazon.addevent.viewmodel.AddEventViewModel
import com.boreal.puertocorazon.core.domain.entity.event.PCPackageToUploadModel
import com.boreal.puertocorazon.core.utils.formatCurrency
import com.boreal.puertocorazon.uisystem.databinding.PcPackageToUploadItemBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PCPackagesAddEventFragment : CUBaseFragment<PcPackagesAddEventFragmentBinding>() {

    val viewModel: AddEventViewModel by sharedViewModel()

    val adapterRecyclerPackages by lazy {
        GAdapter<PcPackageToUploadItemBinding, PCPackageToUploadModel>(
            uiR.layout.pc_package_to_upload_item,
            AsyncDifferConfig.Builder(object : DiffUtil.ItemCallback<PCPackageToUploadModel>() {
                override fun areItemsTheSame(
                    oldItem: PCPackageToUploadModel,
                    newItem: PCPackageToUploadModel
                ) = oldItem == newItem

                override fun areContentsTheSame(
                    oldItem: PCPackageToUploadModel,
                    newItem: PCPackageToUploadModel
                ) = oldItem.titlePackage == newItem.titlePackage

            }).build(),
            holderCallback = { bindingElement, model, list, adapter, position ->
                bindingElement.apply {

                    containerPackageEmpty.onClick {
                        PCAddPackage(list = list) { packageModel ->
                            adapter.replaceAt(packageModel, position)
                            adapter.notifyItemChanged(position)
                        }.show(getSupportFragmentManager(), "odmod")
                    }
                    if (model.empty) {
                        containerPackageEmpty.showView()
                        containerPackageFilled.hideView()
                        btnDeletePackage.hideView()
                    } else {
                        if (model.adult != 0L) {
                            tvCountAdults.text =
                                if (model.adult.toInt() > 1) "${model.adult} Adultos" else "${model.adult} Adulto"
                        }
                        if (model.child != 0L) {
                            tvCountChildren.text =
                                if (model.child.toInt() > 1) "${model.child} Niños / as" else "${model.child} Niño / a"
                        }
                        tvNamePackage.text = model.titlePackage
                        tvPricePackage.text = model.price.formatCurrency()
                        containerPackageEmpty.hideView()
                        containerPackageFilled.showView()
                        btnPayPackage.onClick {
                            //payment flow
                        }
                        btnDeletePackage.showView()
                        btnDeletePackage.onClick {
                            if (list.size > 1) {
                                adapter.removeAt(position)
                            } else if (list.size == 1) {
                                binding.btnAddPackage.invisibleView()
                                model.apply {
                                    empty = true
                                    titlePackage = ""
                                    adult = 0L
                                    child = 0L
                                    price = 0
                                }
                                adapter.notifyItemChanged(position)
                            }

                        }
                    }

                }
            }, onListChanged = { _, currentList ->
                binding.apply {
                    if (currentList.size == 1) {
                        mRecyclerPackages.itemPercent(1.0)

                    } else if (currentList.size == 2) {
                        mRecyclerPackages.itemPercent(.88)
                    }
                    if (currentList.indexOfFirst { it.empty } == -1) {
                        binding.btnAddPackage.showView()
                    } else {
                        binding.btnAddPackage.invisibleView()
                    }
                    mRecyclerPackages.smoothScrollToPosition(currentList.size - 1)
                }
            }
        )
    }

    override fun getLayout() = R.layout.pc_packages_add_event_fragment

    override fun initView() {
        initElements()
    }
}