package com.boreal.puertocorazon.addevent.ui.base

import androidx.navigation.fragment.NavHostFragment
import com.boreal.commonutils.extensions.changeDrawable
import com.boreal.commonutils.extensions.setOnSingleClickListener
import com.boreal.puertocorazon.addevent.R

fun PCBaseAddEventFragment.initElements() {
    binding.apply {
        navigation()
        btnBack.setOnSingleClickListener {
            onFragmentBackPressed()
        }
    }
}

fun PCBaseAddEventFragment.navigation() {
    binding.apply {
        navController =
            (childFragmentManager.findFragmentById(R.id.navigationAddEvent) as NavHostFragment).navController
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.PCBaseAddEventFragment -> {
                    imgBack.changeDrawable(R.drawable.ic_pc_close)
                }
                R.id.PCMainAddEventFragment -> {
                    imgBack.changeDrawable(R.drawable.ic_pc_left_arrow)
                }
                R.id.PCGalleryAddEventFragment -> {
                    imgBack.changeDrawable(R.drawable.ic_pc_left_arrow)
                }
                R.id.PCPackagesAddEventFragment -> {
                    imgBack.changeDrawable(R.drawable.ic_pc_left_arrow)
                }
//            R.id.PCMainAddEventFragment -> {
//
//            }
//            R.id.PCMainAddEventFragment -> {
//
//            }
                else -> {

                }
            }
        }
    }

}