package com.boreal.puertocorazon.showevent.ui.showevent

import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.boreal.commonutils.extensions.setOnSingleClickListener
import com.boreal.puertocorazon.showevent.R

fun PCShowEventFragment.initElements() {
    binding.apply {
        navigation()
        btnClose.setOnSingleClickListener {
            requireActivity().onBackPressed()
        }

        btnGallery.setOnSingleClickListener {
            navController.navigate(
                R.id.PCShowEventGalleryFragment,
            )
        }
    }

}

fun PCShowEventFragment.navigation() {
    binding.apply {
        navController =
            (childFragmentManager.findFragmentById(R.id.containerShowEvent) as NavHostFragment).navController
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.PCShowEventDescriptionFragment -> {
                }
                R.id.PCShowEventGalleryFragment -> {
                }
                R.id.PCShowEventPackagesFragment -> {
                }
                else -> {

                }
            }
        }
    }

}
