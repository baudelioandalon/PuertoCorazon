package com.boreal.puertocorazon.client.menu.ui

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.boreal.commonutils.extensions.changeDrawable
import com.boreal.commonutils.extensions.hideView
import com.boreal.commonutils.extensions.onClick
import com.boreal.commonutils.extensions.showView
import com.boreal.puertocorazon.client.home.R
import com.boreal.puertocorazon.uisystem.R as uiR
import com.boreal.puertocorazon.client.menu.R as clientMenuR
import com.boreal.puertocorazon.payments.R as paymentR
import com.boreal.puertocorazon.ticket.R as ticketR
import com.boreal.puertocorazon.maps.R as mapR
import com.boreal.puertocorazon.showevent.R as showEventR

fun PCClientMenuFragment.initElements() {
    val navHostFragment =
        childFragmentManager.findFragmentById(clientMenuR.id.navigationMenu) as NavHostFragment
    navController = navHostFragment.navController
    binding.apply {
        userProfile = mainViewModel.getImageProfile()
        mainViewModel.hideSplash()
        findNavController().addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                showEventR.id.PCShowEventFragment, paymentR.id.PCCartShoppingFragment -> {
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
            findNavController().navigate(paymentR.id.pc_payment_graph)
        }

        imgHome.onClick {
            if (navController.currentDestination?.label == "PCClientHomeFragment") {
                return@onClick
            }
            imgHome.changeDrawable(uiR.drawable.ic_pc_home_selected)
            imgTicket.changeDrawable(uiR.drawable.ic_pc_ticket)
            imgMap.changeDrawable(uiR.drawable.ic_pc_locations)
            mainViewModel.navigateToHome()
        }

        imgTicket.onClick {
            if (navController.currentDestination?.label == "PCTicketFragment") {
                return@onClick
            }
            imgHome.changeDrawable(uiR.drawable.ic_pc_home)
            imgTicket.changeDrawable(uiR.drawable.ic_pc_ticket_selected)
            imgMap.changeDrawable(uiR.drawable.ic_pc_locations)
            mainViewModel.navigateToTicket()
        }

        imgMap.onClick {
            if (navController.currentDestination?.label == "PCMapFragment") {
                return@onClick
            }
            imgHome.changeDrawable(uiR.drawable.ic_pc_home)
            imgTicket.changeDrawable(uiR.drawable.ic_pc_ticket)
            imgMap.changeDrawable(uiR.drawable.ic_pc_locations_selected)
            mainViewModel.navigateToMap()
        }

        mainViewModel.goToPayment = {
            onFragmentBackPressed(true)
            findNavController().navigate(paymentR.id.pc_payment_graph)
        }

        mainViewModel.navToTicket = {
            navController.navigate(ticketR.id.pc_ticket_graph)
        }

        mainViewModel.navToMap = {
            navController.navigate(mapR.id.pc_map_graph)
        }

        mainViewModel.navToHome = {
            navController.navigate(R.id.pc_client_home_graph)
        }

        drawMenu()
    }
}

fun PCClientMenuFragment.drawMenu() {
    binding.apply {
        if (navController.currentDestination?.label == "PCClientHomeFragment") {
            imgHome.changeDrawable(uiR.drawable.ic_pc_home_selected)
            imgTicket.changeDrawable(uiR.drawable.ic_pc_ticket)
            imgMap.changeDrawable(uiR.drawable.ic_pc_locations)
        } else if (navController.currentDestination?.label == "PCTicketFragment") {
            imgHome.changeDrawable(uiR.drawable.ic_pc_home)
            imgTicket.changeDrawable(uiR.drawable.ic_pc_ticket_selected)
            imgMap.changeDrawable(uiR.drawable.ic_pc_locations)
        } else if (navController.currentDestination?.label == "PCMapFragment") {
            imgHome.changeDrawable(uiR.drawable.ic_pc_home)
            imgTicket.changeDrawable(uiR.drawable.ic_pc_ticket)
            imgMap.changeDrawable(uiR.drawable.ic_pc_locations_selected)
        }
    }
}