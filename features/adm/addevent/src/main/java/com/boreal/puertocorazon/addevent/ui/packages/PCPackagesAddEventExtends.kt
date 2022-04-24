package com.boreal.puertocorazon.addevent.ui.packages

import androidx.recyclerview.widget.LinearSnapHelper
import com.boreal.commonutils.extensions.getSupportFragmentManager
import com.boreal.commonutils.extensions.setOnSingleClickListener
import com.boreal.puertocorazon.core.domain.entity.event.PCPackageToUploadModel
import com.boreal.puertocorazon.core.utils.formatCurrency

fun PCPackagesAddEventFragment.initElements() {
    binding.apply {
        initAdapter()
        var priceTempAdult = 0L
        var priceTempChild = 0L
        if (viewModel.isPriceAdultValid()) {
            tvPriceAdultTicket.text = viewModel.getPriceAdult().formatCurrency()
        }
        if (viewModel.isPriceChildValid()) {
            tvPriceChildTicket.text = viewModel.getPriceChildren().formatCurrency()
        }
        btnTicketAdult.setOnSingleClickListener {
            PCAddPriceTicket("Adulto") { priceTicket ->
                tvPriceAdultTicket.text = priceTicket.toLong().formatCurrency()
                priceTempAdult = priceTicket.toLong()
                tvErrorMessage.text = ""
            }.show(getSupportFragmentManager(), "odmod")
        }

        btnTicketChild.setOnSingleClickListener {
            PCAddPriceTicket("Infantil") { priceTicket ->
                tvPriceChildTicket.text = priceTicket.toLong().formatCurrency()
                priceTempChild = priceTicket.toLong()
            }.show(getSupportFragmentManager(), "odmod")
        }

        btnSave.setOnSingleClickListener {
            if (priceTempAdult != 0L) {
                viewModel.setPriceAdult(priceTempAdult)
                viewModel.setPriceChildren(priceTempChild)
                onFragmentBackPressed()
            } else {
                tvErrorMessage.text = "Agrega precio al boleto"
            }
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