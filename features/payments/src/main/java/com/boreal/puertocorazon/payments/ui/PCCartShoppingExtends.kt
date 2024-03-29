package com.boreal.puertocorazon.payments.ui

import com.boreal.commonutils.extensions.*
import com.boreal.puertocorazon.core.constants.NONE
import com.boreal.puertocorazon.core.domain.entity.payment.PCCardModel
import com.boreal.puertocorazon.core.domain.entity.payment.PCTypeCard
import com.boreal.puertocorazon.core.domain.entity.shopping.PCShoppingModel
import com.boreal.puertocorazon.core.utils.formatCurrency
import com.boreal.puertocorazon.payments.R
import com.boreal.puertocorazon.uisystem.R as uiR

fun PCCartShoppingFragment.initElements() {
    binding.apply {
        btnBack.onClick {
            onFragmentBackPressed()
        }
        initAdapter(mainViewModel.getShoppingList())
        mainViewModel.shoppingChanged = { listShopping ->
            sumTotal(listShopping)
            if (listShopping.isEmpty()) {
                tvSubtotalIva.text = "$0"
            }
        }
        btnPay.onClick {
            if (mainViewModel.getShoppingList().isEmpty()) {
                showToast("No hay articulos en el carrito de compra")
                return@onClick
            }
            viewModel.requestPayment(
                idClient = mainViewModel.getIdUser(),
                nameUser = mainViewModel.getNameUser(),
                emailUser = mainViewModel.getEmailUser(),
                0,
                mainViewModel.getShoppingListToMap()
            )

        }

        initCardAdapter(mainViewModel.getCardList())
        btnCard.onClick {
            if (mainViewModel.getShoppingList().isEmpty()) {
                showToast("No hay articulos en el carrito de compra")
                return@onClick
            }
            if (mainViewModel.getCardList().isEmpty()) {
//                findNavController().navigate(R.id.PCAddCardFragment)
                btnPay.performClick()
            } else {
                //Open show cards
            }
        }

    }
}

fun PCCartShoppingFragment.initCardAdapter(cardList: ArrayList<PCCardModel>) {
    binding.apply {
        if (cardList.isEmpty()) {
            tvAddCard.showView()
            tvNumberCard.hideView()
            tvAliasCard.hideView()
            imgTypeCard.changeDrawable(uiR.drawable.ic_pc_more)
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
                    uiR.drawable.ic_pc_visa
                }
                else -> {
                    uiR.drawable.ic_pc_master_card
                }
            }
        )

        tvAliasCard.text =
            if (card.alias == NONE || card.alias.isEmpty()) card.typeCard + card.cardNumber.takeLast(
                4
            ) else card.alias

        tvNumberCard.text = getString(uiR.string.card_hidden, card.cardNumber.takeLast(4))
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
        tvSubtotalIva.text = subtotal.formatCurrency()
        tvTotal.text = subtotal.formatCurrency()
    }
}
