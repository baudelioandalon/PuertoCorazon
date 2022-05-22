package com.boreal.puertocorazon.payments.ui

import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import com.boreal.commonutils.base.CUBaseFragment
import com.boreal.commonutils.extensions.notInvisibleIf
import com.boreal.commonutils.extensions.onClick
import com.boreal.commonutils.utils.GAdapter
import com.boreal.puertocorazon.core.domain.entity.shopping.PCShoppingModel
import com.boreal.puertocorazon.core.utils.formatCurrency
import com.boreal.puertocorazon.core.viewmodel.PCMainViewModel
import com.boreal.puertocorazon.payments.R
import com.boreal.puertocorazon.payments.databinding.PcCartShoppingBinding
import com.boreal.puertocorazon.payments.databinding.PcItemShoppingBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PCCartShoppingFragment : CUBaseFragment<PcCartShoppingBinding>() {

    val mainViewModel: PCMainViewModel by sharedViewModel()

    val adapterRecyclerShopping by lazy {
        GAdapter<PcItemShoppingBinding, PCShoppingModel>(
            R.layout.pc_item_shopping,
            AsyncDifferConfig.Builder(object : DiffUtil.ItemCallback<PCShoppingModel>() {
                override fun areItemsTheSame(
                    oldItem: PCShoppingModel,
                    newItem: PCShoppingModel
                ) = oldItem == newItem

                override fun areContentsTheSame(
                    oldItem: PCShoppingModel,
                    newItem: PCShoppingModel
                ) =
                    oldItem.countItem == newItem.countItem &&
                            oldItem.idEvent == newItem.idEvent &&
                            oldItem.namePackage == newItem.namePackage &&
                            oldItem.imageEvent == newItem.imageEvent

            }).build(),
            holderCallback = { bindingElement, model, list, adapter, position ->
                bindingElement.apply {
                    modelShoppingCart = model
                    countItem = model.countItem.toString()
                    tvPriceElement.text = model.priceElement.toLong().formatCurrency()
                    tvNamePackage.notInvisibleIf(model.isPackage) {
                        tvNamePackage.text = model.namePackage
                    }
                    tvCountChildren.notInvisibleIf(model.countChild > 0) {
                        val plural = if (model.countChild > 1) {
                            "s"
                        } else {
                            ""
                        }
                        tvCountChildren.text = "${model.countChild} x Niño$plural / a$plural"
                    }
                    tvCountAdults.notInvisibleIf(model.countAdult > 0) {
                        val plural = if (model.countAdult > 1) {
                            "s"
                        } else {
                            ""
                        }
                        tvCountAdults.text = "${model.countAdult} x Adulto$plural"
                    }
                    btnDeleteElement.onClick {
                        adapter.remove(model)
                    }
                    btnLess.onClick {
                        if (model.countItem > 1) {
                            model.countItem -= 1
                            countItem = model.countItem.toString()
                            adapter.notifyListContentChanged()
                        }
                    }
                    btnMore.onClick {
                        if (model.countItem < 99) {
                            model.countItem += 1
                            countItem = model.countItem.toString()
                            adapter.notifyListContentChanged()
                        }
                    }
                }
            }, onListChanged = { previousList, currentList ->
                mainViewModel.setShoppingList(ArrayList(currentList))
            }
        )
    }

    override fun getLayout() = R.layout.pc_cart_shopping

    override fun initView() {
        initElements()
    }
}