package com.boreal.puertocorazon.showevent.ui.sepackages

import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import com.boreal.commonutils.base.CUBaseFragment
import com.boreal.commonutils.extensions.hideView
import com.boreal.commonutils.extensions.onClick
import com.boreal.commonutils.extensions.showToast
import com.boreal.commonutils.utils.GAdapter
import com.boreal.puertocorazon.core.domain.entity.event.PCPackageModel
import com.boreal.puertocorazon.core.domain.entity.shopping.PCShoppingModel
import com.boreal.puertocorazon.core.utils.formatCurrency
import com.boreal.puertocorazon.core.viewmodel.PCMainViewModel
import com.boreal.puertocorazon.showevent.R
import com.boreal.puertocorazon.uisystem.R as uiR
import com.boreal.puertocorazon.showevent.databinding.PcShowEventPackagesFragmentBinding
import com.boreal.puertocorazon.uisystem.databinding.PcPackageItemBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

//fun Float.currencyFormat(noDecimal: Boolean = true) = if (noDecimal) {
//    "$${toInt()}"
//} else {
//    "$${String.format("%.2f", this)}"
//}


class PCShowEventPackagesFragment :
    CUBaseFragment<PcShowEventPackagesFragmentBinding>() {

    val mainViewModel: PCMainViewModel by sharedViewModel()

    val adapterRecyclerPackages by lazy {
        GAdapter<PcPackageItemBinding, PCPackageModel>(
            uiR.layout.pc_package_item,
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
                    model.apply {
                        tvPricePackage.text = price.formatCurrency()
                        tvNamePackage.text = model.titlePackage
                        btnAddPackage.onClick {
                            mainViewModel.getEventSelected().apply {
                                mainViewModel.addShopping(
                                    PCShoppingModel(
                                        idEvent = idEvent,
                                        idPackage = model.idPackage,
                                        imageEvent = homeImageUrl,
                                        titleEvent = title,
                                        countAdult = model.adult.toFloat().toInt(),
                                        countChild = model.child.toFloat().toInt(),
                                        priceElement = model.price.toFloat().toInt(),
                                        isPackage = true,
                                        namePackage = model.titlePackage
                                    )
                                )
                            }
                            showToast("Boleto agregado al carrito")
                        }
                        if (adult == 0L) {
                            tvCountAdults.hideView()
                        } else {
                            tvCountAdults.text = "$adult Adulto${
                                if (adult > 1) {
                                    "s"
                                } else {
                                    ""
                                }
                            }"
                        }
                        if (child == 0L) {
                            tvCountChildren.hideView()
                        } else {
                            val plural = if (child > 1) {
                                "s"
                            } else {
                                ""
                            }
                            tvCountChildren.text = "$child Niño$plural / a$plural"
                        }
                    }
                }
            }
        )
    }

    //    , PCShowEventPackagesViewModel
    override fun getLayout() = R.layout.pc_show_event_packages_fragment

    override fun initView() {
        initElements()
    }
}