package com.boreal.puertocorazon.client.menu.ui

import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.boreal.commonutils.extensions.changeDrawable
import com.boreal.commonutils.extensions.hideView
import com.boreal.commonutils.extensions.onClick
import com.boreal.commonutils.extensions.showView
import com.boreal.puertocorazon.client.home.R

fun PCClientMenuFragment.initElements() {
    val navHostFragment =
        childFragmentManager.findFragmentById(R.id.navigationMenu) as NavHostFragment
    navController = navHostFragment.navController
    binding.apply {
        userProfile = mainViewModel.getImageProfile()
        mainViewModel.hideSplash()
        findNavController().addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.PCShowEventFragment, R.id.PCCartShoppingFragment -> {
                    bottomMenu.hideView()
                }
                else -> {
                    bottomMenu.showView()
                }
            }
        }

        btnNotifications.onClick {
            mainViewModel.signOutUser()
        }

        btnCartShopping.onClick {
            findNavController().navigate(R.id.pc_payment_graph)
        }

        imgTicket.onClick {
            if (navController.currentDestination?.label == "PCTicketFragment") {
                return@onClick
            }
            imgHome.changeDrawable(R.drawable.ic_pc_home)
            imgTicket.changeDrawable(R.drawable.ic_pc_ticket_selected)
            imgMap.changeDrawable(R.drawable.ic_pc_locations)
            mainViewModel.navigateToTicket()
        }

        imgHome.onClick {
            if (navController.currentDestination?.label == "PCClientHomeFragment") {
                return@onClick
            }
            imgHome.changeDrawable(R.drawable.ic_pc_home_selected)
            imgTicket.changeDrawable(R.drawable.ic_pc_ticket)
            imgMap.changeDrawable(R.drawable.ic_pc_locations)
            mainViewModel.navigateToHome()
        }

        imgMap.onClick {
            Toast.makeText(requireActivity(), "Press", Toast.LENGTH_SHORT).show()
            if (navController.currentDestination?.label == "PCMapFragment") {
                return@onClick
            }
            imgHome.changeDrawable(R.drawable.ic_pc_home)
            imgTicket.changeDrawable(R.drawable.ic_pc_ticket)
            imgMap.changeDrawable(R.drawable.ic_pc_locations_selected)
            mainViewModel.navigateToMap()
        }

        mainViewModel.goToPayment = {
            onFragmentBackPressed(true)
            findNavController().navigate(R.id.pc_payment_graph)
        }
        drawMenu()
    }
}

fun PCClientMenuFragment.drawMenu() {
    binding.apply {
        if (navController.currentDestination?.label == "PCClientHomeFragment") {
            imgHome.changeDrawable(R.drawable.ic_pc_home_selected)
            imgTicket.changeDrawable(R.drawable.ic_pc_ticket)
            imgMap.changeDrawable(R.drawable.ic_pc_locations)
        } else if (navController.currentDestination?.label == "PCTicketFragment") {
            imgHome.changeDrawable(R.drawable.ic_pc_home)
            imgTicket.changeDrawable(R.drawable.ic_pc_ticket_selected)
            imgMap.changeDrawable(R.drawable.ic_pc_locations)
        } else if (navController.currentDestination?.label == "PCMapFragment"){
            imgHome.changeDrawable(R.drawable.ic_pc_home)
            imgTicket.changeDrawable(R.drawable.ic_pc_ticket)
            imgMap.changeDrawable(R.drawable.ic_pc_locations_selected)
        }
    }
}