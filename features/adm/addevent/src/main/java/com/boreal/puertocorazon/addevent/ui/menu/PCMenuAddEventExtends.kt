package com.boreal.puertocorazon.addevent.ui.menu

import androidx.navigation.fragment.findNavController
import com.boreal.commonutils.extensions.setOnSingleClickListener
import com.boreal.puertocorazon.addevent.R

fun PCMenuAddEventFragment.initElements() {
    binding.apply {
        btnMain.setOnSingleClickListener {
            findNavController().navigate(R.id.PCMainAddEventFragment)
        }
        btnGallery.setOnSingleClickListener {
            findNavController().navigate(R.id.PCGalleryAddEventFragment)
        }
        btnPackages.setOnSingleClickListener {
            findNavController().navigate(R.id.PCPackagesAddEventFragment)
        }
        btnDetails.setOnSingleClickListener {
            findNavController().navigate(R.id.PCDetailsAddEventFragment)
        }
        btnRequirements.setOnSingleClickListener {
            findNavController().navigate(R.id.PCRequirementsAddEventFragment)
        }
        btnDetails.performClick()
    }
}