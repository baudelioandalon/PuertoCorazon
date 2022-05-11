package com.boreal.puertocorazon.payments.ui

import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import com.boreal.commonutils.base.CUBaseFragment
import com.boreal.commonutils.extensions.onClick
import com.boreal.commonutils.utils.GAdapter
import com.boreal.puertocorazon.core.domain.entity.shopping.PCShoppingModel
import com.boreal.puertocorazon.payments.R
import com.boreal.puertocorazon.payments.databinding.PcCartShoppingBinding
import com.boreal.puertocorazon.payments.databinding.PcItemShoppingBinding

class PCCartShoppingFragment : CUBaseFragment<PcCartShoppingBinding>() {

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
                ) = oldItem == newItem

            }).build(),
            holderCallback = { bindingElement, model, list, adapter, position ->
                bindingElement.apply {
                    countItem = model.countItem.toString()
                    btnDeleteElement.onClick {
                        adapter.remove(model)
                    }
                    btnLess.onClick {
                        if (model.countItem > 1) {
                            model.countItem -= 1
                            countItem = model.countItem.toString()
                        }
                    }
                    btnMore.onClick {
                        if (model.countItem < 99) {
                            model.countItem += 1
                            countItem = model.countItem.toString()
                        }
                    }
                }
            }
        )
    }

    override fun getLayout() = R.layout.pc_cart_shopping

    override fun initView() {
        initElements()
    }
}