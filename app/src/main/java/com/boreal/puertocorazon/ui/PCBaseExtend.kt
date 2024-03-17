package com.boreal.puertocorazon.ui

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.boreal.puertocorazon.R
import com.boreal.puertocorazon.core.utils.DialogGenericFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.boreal.puertocorazon.core.R as coreR

fun PCBaseActivity.initElements() {
    binding.apply {
        splashDialog = DialogGenericFragment(
            layout = coreR.layout.splash
        ) { dialogBinding, dialogFragment ->
            dialogBinding.apply {

            }
        }
        mainViewModel.showSplash()
        lifecycleScope.launch(Dispatchers.Main) {
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.navigationBase) as NavHostFragment
            navController = navHostFragment.navController
        }

    }
}