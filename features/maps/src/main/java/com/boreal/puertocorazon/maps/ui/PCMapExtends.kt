package com.boreal.puertocorazon.maps.ui

import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.boreal.puertocorazon.maps.R

fun PCMapFragment.initElements(){

    binding.apply {
        mainViewModel.navToHome = {
            try {
                findNavController().popBackStack()

            } catch (e: Exception) {

            }
        }
    }

    mainViewModel.eventList.value

}