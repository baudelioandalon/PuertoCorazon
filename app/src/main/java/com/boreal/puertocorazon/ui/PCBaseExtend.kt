package com.boreal.puertocorazon.ui

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.boreal.puertocorazon.R
import com.boreal.puertocorazon.core.utils.DialogGenericFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun PCBaseActivity.initElements() {
    binding.apply {
        splashDialog = DialogGenericFragment(
            layout = R.layout.splash
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