package com.boreal.puertocorazon.payments.ui

import androidx.navigation.fragment.findNavController
import com.boreal.commonutils.extensions.*
import com.boreal.puertocorazon.core.constants.NONE
import com.boreal.puertocorazon.core.domain.entity.payment.PCCardModel
import com.boreal.puertocorazon.core.domain.entity.payment.PCTypeCard
import com.boreal.puertocorazon.core.domain.entity.payment.PCTypePayment
import com.boreal.puertocorazon.core.domain.entity.shopping.PCShoppingModel
import com.boreal.puertocorazon.core.utils.formatCurrency
import com.boreal.puertocorazon.payments.R
import com.boreal.puertocorazon.payments.ui.paymentselector.PCPaymentSelector

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
        btnPay.onClick {
            PCPaymentSelector { typePaymentSelected ->
                typePaymentSelected
            }.show(getSupportFragmentManager(), "odmod")
        }

        initCardAdapter(mainViewModel.getCardList())
        btnCard.onClick {
            if (mainViewModel.getCardList().isEmpty()) {
                findNavController().navigate(R.id.PCAddCardFragment)
            } else {
                //Open show cards
            }
        }
        findNavController().navigate(R.id.PCAddCardFragment)
    }
}

fun PCCartShoppingFragment.initCardAdapter(cardList: ArrayList<PCCardModel>) {
    binding.apply {
        if (cardList.isEmpty()) {
            tvAddCard.showView()
            tvNumberCard.hideView()
            tvAliasCard.hideView()
            imgTypeCard.changeDrawable(R.drawable.ic_pc_more)
        } else {
            tvAddCard.hideView()
            tvNumberCard.showView()
            tvAliasCard.showView()
            val favoriteCard = cardList.find { it.default }
            fillCard(favoriteCard ?: cardList[0])
        }
    }
}

private fun PCCartShoppingFragment.fillCard(card: PCCardModel) {
    binding.apply {
        imgTypeCard.changeDrawable(
            when (card.typeCard) {
                PCTypeCard.VISA.name -> {
                    R.drawable.ic_pc_visa
                }
                else -> {
                    R.drawable.ic_pc_master_card
                }
            }
        )

        tvAliasCard.text =
            if (card.alias == NONE || card.alias.isEmpty()) card.typeCard + card.cardNumber.takeLast(
                4
            ) else card.alias

        tvNumberCard.text = getString(R.string.card_hidden, card.cardNumber.takeLast(4))
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

private fun PCCartShoppingFragment.sumTotal(listShopping: List<PCShoppingModel>) {
    binding.apply {
        val subtotal = listShopping.sumOf { (it.countItem * it.priceElement) }.toLong()
        val iva = (subtotal * .16).toLong()
        tvSubtotal.text = subtotal.formatCurrency()
        tvSubtotalIva.text = iva.formatCurrency()
        tvTotal.text = (subtotal + iva).formatCurrency()
    }
}
