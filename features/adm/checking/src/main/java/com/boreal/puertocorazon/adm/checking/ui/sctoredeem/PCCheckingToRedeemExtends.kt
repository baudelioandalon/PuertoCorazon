package com.boreal.puertocorazon.adm.checking.ui.sctoredeem

import com.boreal.puertocorazon.core.domain.entity.payment.PCPackageTicketModel

fun PCCheckingToRedeemFragment.initElements() {
    binding.apply {
        recyclerViewToRedeem.apply {
            adapter(adapterRecyclerToRedeem)
        }
    }
}

fun PCCheckingToRedeemFragment.loadRecyclerEvent(response: List<PCPackageTicketModel>) {
    adapterRecyclerToRedeem.submitList(response.filter { it.isNotUsed() })
}