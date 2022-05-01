package com.boreal.puertocorazon.addevent.ui.packages

import androidx.recyclerview.widget.LinearSnapHelper
import com.boreal.commonutils.extensions.getSupportFragmentManager
import com.boreal.commonutils.extensions.onClick
import com.boreal.puertocorazon.addevent.ui.packages.addpackage.PCAddPackage
import com.boreal.puertocorazon.addevent.ui.packages.addprice.PCAddPriceTicket
import com.boreal.puertocorazon.core.domain.entity.event.PCPackageToUploadModel
import com.boreal.puertocorazon.core.utils.formatCurrency

fun PCPackagesAddEventFragment.initElements() {
    binding.apply {
        initAdapter()
        var priceTempAdult = viewModel.getPriceAdult()
        var priceTempChild = viewModel.getPriceChildren()
        if (viewModel.isPriceAdultValid()) {
            tvPriceAdultTicket.text = viewModel.getPriceAdult().formatCurrency()
        }
        if (viewModel.isPriceChildValid()) {
            tvPriceChildTicket.text = viewModel.getPriceChildren().formatCurrency()
        }
        btnTicketAdult.onClick {
            PCAddPriceTicket("Adulto") { priceTicket ->
                tvPriceAdultTicket.text = priceTicket.toLong().formatCurrency()
                priceTempAdult = priceTicket.toLong()
                tvErrorMessage.text = ""
            }.show(getSupportFragmentManager(), "odmod")
        }

        btnTicketChild.onClick {
            PCAddPriceTicket("Infantil") { priceTicket ->
                tvPriceChildTicket.text = priceTicket.toLong().formatCurrency()
                priceTempChild = priceTicket.toLong()
            }.show(getSupportFragmentManager(), "odmod")
        }

        btnSave.onClick {
            if (priceTempAdult != 0L) {
                viewModel.setPriceAdult(priceTempAdult)
                viewModel.setPriceChildren(priceTempChild)
                viewModel.setPackages(adapterRecyclerPackages.currentList)
                onFragmentBackPressed()
            } else {
                tvErrorMessage.text = "Agrega precio al boleto"
            }
        }

        btnAddPackage.onClick {
            PCAddPackage(list = adapterRecyclerPackages.currentList) { packageModel ->
                adapterRecyclerPackages.add(packageModel)
            }.show(getSupportFragmentManager(), "odmod")
        }
    }
}

fun PCPackagesAddEventFragment.initAdapter() {
    binding.mRecyclerPackages.apply {
        LinearSnapHelper().attachToRecyclerView(this)
        adapter = adapterRecyclerPackages
    }
    if (viewModel.getPackages().isEmpty()) {
        adapterRecyclerPackages.submitList(arrayListOf(PCPackageToUploadModel()))
    } else {
        adapterRecyclerPackages.submitList(viewModel.getPackages())
    }

}