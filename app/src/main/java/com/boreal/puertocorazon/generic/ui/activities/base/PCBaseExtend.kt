package com.boreal.puertocorazon.generic.ui.activities.base

import androidx.navigation.fragment.NavHostFragment
import com.boreal.commonutils.extensions.hideView
import com.boreal.commonutils.extensions.showView
import com.boreal.puertocorazon.R

fun PCBaseActivity.initElements() {
    mBinding.apply {

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navigationBase) as NavHostFragment
        val navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.PCShowEventFragment -> {
                    bottomMenu.hideView()
                }
                else -> {
                    bottomMenu.showView()
                }
            }
        }
    }
}