package com.boreal.puertocorazon.addevent.ui.menu

import android.net.Uri
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.boreal.commonutils.extensions.onClick
import com.boreal.puertocorazon.addevent.R
import com.boreal.puertocorazon.core.utils.clearText
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun PCMenuAddEventFragment.initElements() {
    binding.apply {
        btnMain.onClick {
            findNavController().navigate(R.id.PCMainAddEventFragment)
        }
        btnGallery.onClick {
            findNavController().navigate(R.id.PCGalleryAddEventFragment)
        }
        btnPackages.onClick {
            findNavController().navigate(R.id.PCPackagesAddEventFragment)
        }
        btnDetails.onClick {
            findNavController().navigate(R.id.PCDetailsAddEventFragment)
        }
        btnRequirements.onClick {
            findNavController().navigate(R.id.PCRequirementsAddEventFragment)
        }
        btnSave.onClick {
            addEventViewModel.apply {
                if (getEventTitle().isEmpty() ||
                    getEventSubtitle().isEmpty() ||
                    getEventDescription().isEmpty() ||
                    getHomeImage() == Uri.EMPTY
                ) {
                    changeText("Ve a la opcion Principal")
                    return@onClick
                }
                if (getGallery().isEmpty() || getMainImage() == Uri.EMPTY) {
                    changeText("Agrega una imagen a la galeria y una foto principal")
                    return@onClick
                }
                if (!isPriceAdultValid()) {
                    changeText("Agrega el precio al boleto de adulto")
                    return@onClick
                }
                if (!isScheduleValid()) {
                    changeText("Ve a Detalles y agrega la fecha de tu evento")
                    return@onClick
                }
                if (!requirementsChanged) {
                    changeText("Ve a Requerimientos y elige los que se adecuen mas a tu evento")
                    return@onClick
                }
                setEvent()
            }
        }
    }
}

fun PCMenuAddEventFragment.changeText(messageToShow: String) {
    binding.apply {
        lifecycleScope.launch {
            tvErrorMessage.text = messageToShow
            btnSave.isEnabled = false
            delay(3000)
            btnSave.isEnabled = true
            tvErrorMessage.clearText()
        }
    }
}