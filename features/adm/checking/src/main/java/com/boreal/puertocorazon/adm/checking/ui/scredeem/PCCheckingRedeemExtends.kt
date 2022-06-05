package com.boreal.puertocorazon.adm.checking.ui.scredeem

import com.boreal.puertocorazon.core.domain.entity.payment.PCPackageTicketModel

fun PCCheckingRedeemFragment.initElements() {
    binding.apply {
        recyclerViewRedeem.apply {
            adapter(adapterRecyclerRedeem)
        }
    }
}

fun PCCheckingRedeemFragment.loadRecyclerEvent(response: List<PCPackageTicketModel>) {
    adapterRecyclerRedeem.submitList(response.filter { it.isUsed() })
}