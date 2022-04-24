package com.boreal.puertocorazon.addevent.ui.packages

import androidx.recyclerview.widget.LinearSnapHelper
import com.boreal.commonutils.extensions.getSupportFragmentManager
import com.boreal.commonutils.extensions.setOnSingleClickListener
import com.boreal.puertocorazon.core.domain.entity.event.PCPackageToUploadModel
import com.boreal.puertocorazon.core.utils.formatCurrency

fun PCPackagesAddEventFragment.initElements() {
    binding.apply {
        initAdapter()

        btnTicketAdult.setOnSingleClickListener {
            PCAddPriceTicket ("Adulto"){ priceTicket ->
                tvPriceAdultTicket.text = priceTicket.toLong().formatCurrency()
            }.show(getSupportFragmentManager(), "odmod")
        }

        btnTicketChild.setOnSingleClickListener {
            PCAddPriceTicket("Infantil") { priceTicket ->
                tvPriceChildTicket.text = priceTicket.toLong().formatCurrency()

            }.show(getSupportFragmentManager(), "odmod")
        }
    }
}

fun PCPackagesAddEventFragment.initAdapter() {
    binding.mRecyclerPackages.apply {
        LinearSnapHelper().attachToRecyclerView(this)
        adapter = adapterRecyclerPackages
    }
    adapterRecyclerPackages.submitList(arrayListOf(PCPackageToUploadModel()))

}