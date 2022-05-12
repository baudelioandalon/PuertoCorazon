package com.boreal.puertocorazon.payments.ui

import com.boreal.commonutils.extensions.onClick
import com.boreal.puertocorazon.core.domain.entity.shopping.PCShoppingModel
import com.boreal.puertocorazon.core.utils.formatCurrency

fun PCCartShoppingFragment.initElements() {
    binding.apply {
        btnBack.onClick {
            onFragmentBackPressed()
        }
        initAdapter(mainViewModel.getShoppingList())
        mainViewModel.shoppingChanged = { listShopping ->
            sumTotal(listShopping)
            if (listShopping.isEmpty()) {
                tvSubtotal.text = "$0"
                tvSubtotalIva.text = "$0"
            }
        }
    }
}

private fun PCCartShoppingFragment.initAdapter(listShopping: List<PCShoppingModel>) {
    binding.apply {
        sumTotal(listShopping)
        adapterRecyclerShopping.submitList(listShopping)
        recyclerViewShopping.apply {
            adapter = adapterRecyclerShopping
        }
    }

}

private fun PCCartShoppingFragment.sumTotal(listShopping: List<PCShoppingModel>){
    binding.apply {
        val subtotal = listShopping.sumOf { (it.countItem * it.priceElement) }.toLong()
        val iva = (subtotal * .16).toLong()
        tvSubtotal.text = subtotal.formatCurrency()
        tvSubtotalIva.text = iva.formatCurrency()
        tvTotal.text = (subtotal + iva).formatCurrency()
    }
}
