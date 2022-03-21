package com.boreal.puertocorazon.ui

import androidx.navigation.fragment.NavHostFragment
import com.boreal.commonutils.extensions.hideView
import com.boreal.commonutils.extensions.showView
import com.boreal.puertocorazon.R

fun PCBaseActivity.initElements() {
    mBinding.apply {

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navigationBase) as NavHostFragment
        val navController = navHostFragment.navController


    }
}