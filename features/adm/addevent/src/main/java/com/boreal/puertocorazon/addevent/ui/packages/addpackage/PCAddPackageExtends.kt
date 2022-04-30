package com.boreal.puertocorazon.addevent.ui.packages.addpackage

import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import com.boreal.commonutils.extensions.setOnSingleClickListener
import com.boreal.commonutils.extensions.showToast
import com.boreal.puertocorazon.addevent.R
import com.boreal.puertocorazon.core.domain.entity.event.PCPackageToUploadModel
import com.boreal.puertocorazon.core.utils.onlyText
import com.boreal.puertocorazon.core.utils.toNumber

fun PCAddPackage.initElements() {
    binding.apply {

        txtNamePackage.doAfterTextChanged {
            countainerNamePackage.strokeLineColor =
                ContextCompat.getColor(requireContext(), R.color.blue_edittext)
        }
        btnAddPackage.setOnSingleClickListener {
            if (txtNamePackage.onlyText().isEmpty()) {
                countainerNamePackage.strokeLineColor =
                    ContextCompat.getColor(requireContext(), R.color.red_600)
                return@setOnSingleClickListener
            }
            if (txtPricePackage.getIntegers() == 0) {
                showToast("El precio no puede ser 0")
                return@setOnSingleClickListener
            }
            if (list.find { it.titlePackage == txtNamePackage.onlyText() } != null) {
                showToast("Ya existe un paquete con ese nombre")
                return@setOnSingleClickListener
            }
            packageResult.invoke(
                PCPackageToUploadModel(
                    empty = false,
                    titlePackage = txtNamePackage.onlyText(),
                    adult = txtCountAdult.toNumber(),
                    child = txtCountChild.toNumber(),
                    price = txtPricePackage.getAmount().toLong()
                )
            )
            closeFragment()
        }

        btnLessAdult.setOnSingleClickListener {
            if (txtCountAdult.toNumber<Int>() != 0) {
                val adult = txtCountAdult.toNumber<Int>() - 1
                "$adult x Adulto".also { txtShowAmountAdult.text = it }
                txtCountAdult.text = (adult).toString()
            }
        }

        btnMoreAdult.setOnSingleClickListener {
            if (txtCountAdult.toNumber<Int>() != 99) {
                val adult = txtCountAdult.toNumber<Int>() + 1
                "$adult x Adulto".also { txtShowAmountAdult.text = it }
                txtCountAdult.text = (adult).toString()
            }
        }

        btnLessChild.setOnSingleClickListener {
            if (txtCountChild.toNumber<Int>() != 0) {
                val child = txtCountChild.toNumber<Int>() - 1
                "$child x Niño / a".also { txtShowAmountChild.text = it }
                txtCountChild.text = (child).toString()
            }
        }

        btnMoreChild.setOnSingleClickListener {
            if (txtCountChild.toNumber<Int>() != 99) {
                val child = txtCountChild.toNumber<Int>() + 1
                "$child x Niño / a".also { txtShowAmountChild.text = it }
                txtCountChild.text = (child).toString()
            }
        }

        btnBack.setOnSingleClickListener {
            closeFragment()
        }
    }
}