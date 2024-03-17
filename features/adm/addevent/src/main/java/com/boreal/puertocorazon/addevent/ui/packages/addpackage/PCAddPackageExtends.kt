package com.boreal.puertocorazon.addevent.ui.packages.addpackage

import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import com.boreal.commonutils.extensions.onClick
import com.boreal.commonutils.extensions.showToast
import com.boreal.puertocorazon.addevent.R
import com.boreal.puertocorazon.uisystem.R as uiR
import com.boreal.puertocorazon.core.domain.entity.event.PCPackageToUploadModel
import com.boreal.puertocorazon.core.utils.onlyText
import com.boreal.puertocorazon.core.utils.toNumber

fun PCAddPackage.initElements() {
    binding.apply {

        txtNamePackage.doAfterTextChanged {
            countainerNamePackage.strokeLineColor =
                ContextCompat.getColor(requireContext(), uiR.color.blue_edittext)
        }
        btnAddPackage.onClick {
            if (txtNamePackage.onlyText().isEmpty()) {
                countainerNamePackage.strokeLineColor =
                    ContextCompat.getColor(requireContext(), uiR.color.red_600)
                return@onClick
            }
            if (txtPricePackage.getIntegers() == 0) {
                showToast("El precio no puede ser 0")
                return@onClick
            }
            if (list.find { it.titlePackage == txtNamePackage.onlyText() } != null) {
                showToast("Ya existe un paquete con ese nombre")
                return@onClick
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

        btnLessAdult.onClick {
            if (txtCountAdult.toNumber<Int>() != 0) {
                val adult = txtCountAdult.toNumber<Int>() - 1
                "$adult x Adulto".also { txtShowAmountAdult.text = it }
                txtCountAdult.text = (adult).toString()
            }
        }

        btnMoreAdult.onClick {
            if (txtCountAdult.toNumber<Int>() != 99) {
                val adult = txtCountAdult.toNumber<Int>() + 1
                "$adult x Adulto".also { txtShowAmountAdult.text = it }
                txtCountAdult.text = (adult).toString()
            }
        }

        btnLessChild.onClick {
            if (txtCountChild.toNumber<Int>() != 0) {
                val child = txtCountChild.toNumber<Int>() - 1
                "$child x Niño / a".also { txtShowAmountChild.text = it }
                txtCountChild.text = (child).toString()
            }
        }

        btnMoreChild.onClick {
            if (txtCountChild.toNumber<Int>() != 99) {
                val child = txtCountChild.toNumber<Int>() + 1
                "$child x Niño / a".also { txtShowAmountChild.text = it }
                txtCountChild.text = (child).toString()
            }
        }

        btnBack.onClick {
            closeFragment()
        }
    }
}