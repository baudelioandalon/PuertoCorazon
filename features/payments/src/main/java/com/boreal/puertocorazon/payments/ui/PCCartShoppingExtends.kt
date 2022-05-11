package com.boreal.puertocorazon.payments.ui

import com.boreal.commonutils.extensions.onClick
import com.boreal.puertocorazon.core.domain.entity.shopping.PCShoppingModel

fun PCCartShoppingFragment.initElements() {
    binding.apply {
        btnBack.onClick {
            onFragmentBackPressed()
        }
        initAdapter(arrayListOf(PCShoppingModel(),PCShoppingModel()))
    }
}

fun PCCartShoppingFragment.initAdapter(packageList: List<PCShoppingModel>) {
    adapterRecyclerShopping.submitList(packageList)
    binding.apply {
        recyclerViewShopping.apply {
            adapter = adapterRecyclerShopping
        }
    }

}
