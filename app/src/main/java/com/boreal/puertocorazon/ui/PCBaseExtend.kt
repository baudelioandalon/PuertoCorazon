package com.boreal.puertocorazon.ui

import androidx.navigation.fragment.NavHostFragment
import com.boreal.puertocorazon.R

fun PCBaseActivity.initElements() {
    binding.apply {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navigationBase) as NavHostFragment
        navController = navHostFragment.navController
    }
}