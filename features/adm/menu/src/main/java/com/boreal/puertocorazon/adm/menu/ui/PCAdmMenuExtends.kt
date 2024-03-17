package com.boreal.puertocorazon.adm.menu.ui

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.boreal.commonutils.extensions.changeDrawable
import com.boreal.commonutils.extensions.hideView
import com.boreal.commonutils.extensions.onClick
import com.boreal.commonutils.extensions.showView
import com.boreal.puertocorazon.adm.menu.R
import com.boreal.puertocorazon.adm.addevent.R as admAddEventR
import com.boreal.puertocorazon.adm.checking.R as admCheckingR
import com.boreal.puertocorazon.showevent.R as showEventR
import com.boreal.puertocorazon.uisystem.R as uiR
import com.boreal.puertocorazon.payments.R as paymentsR
import com.boreal.puertocorazon.adm.home.R as admHomeR
import com.boreal.puertocorazon.maps.R as mapR
import com.boreal.puertocorazon.ticket.R as ticketR

fun PCAdmMenuFragment.initElements() {
    val navHostFragment =
        childFragmentManager.findFragmentById(R.id.navigationMenu) as NavHostFragment
    navController = navHostFragment.navController
    binding.apply {
        userProfile = mainViewModel.getImageProfile()
        mainViewModel.hideSplash()
        findNavController().addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                showEventR.id.PCShowEventFragment,
                paymentsR.id.PCCartShoppingFragment,
                admCheckingR.id.PCCheckingEventFragment -> {
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
            findNavController().navigate(paymentsR.id.pc_payment_graph)
        }

        btnNewEvent.onClick {
            findNavController().navigate(admAddEventR.id.pc_add_event_graph)
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

        imgHome.onClick {
            if (navController.currentDestination?.label == "PCAdmHomeFragment") {
                return@onClick
            }
            imgHome.changeDrawable(uiR.drawable.ic_pc_home_selected)
            imgTicket.changeDrawable(uiR.drawable.ic_pc_ticket)
            imgMap.changeDrawable(uiR.drawable.ic_pc_locations)
            mainViewModel.navigateToHome()
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

        mainViewModel.goToChecking = {
            findNavController().navigate(admCheckingR.id.pc_adm_checking_graph)
        }

        mainViewModel.goToPayment = {
            onFragmentBackPressed(true)
            findNavController().navigate(paymentsR.id.pc_payment_graph)
        }

        mainViewModel.navToTicket = {
            navController.navigate(ticketR.id.pc_ticket_graph)
        }

        mainViewModel.navToMap = {
            navController.navigate(mapR.id.pc_map_graph)
        }

        mainViewModel.navToHome = {
            navController.navigate(admHomeR.id.pc_adm_home_graph)
        }
        drawMenu()
    }
}

fun PCAdmMenuFragment.drawMenu() {
    binding.apply {
        if (navController.currentDestination?.label == "PCAdmHomeFragment") {
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