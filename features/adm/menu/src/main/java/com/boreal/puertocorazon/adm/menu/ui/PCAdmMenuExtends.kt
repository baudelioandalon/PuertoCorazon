package com.boreal.puertocorazon.adm.menu.ui

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.boreal.commonutils.extensions.changeDrawable
import com.boreal.commonutils.extensions.hideView
import com.boreal.commonutils.extensions.onClick
import com.boreal.commonutils.extensions.showView
import com.boreal.puertocorazon.adm.menu.R

fun PCAdmMenuFragment.initElements() {
    val navHostFragment =
        childFragmentManager.findFragmentById(R.id.navigationMenu) as NavHostFragment
    navController = navHostFragment.navController
    binding.apply {
        userProfile = mainViewModel.getImageProfile()
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

        btnNewEvent.onClick {
            findNavController().navigate(R.id.pc_add_event_graph)
        }

        imgTicket.onClick {
            if (navController.currentDestination?.label == "PCTicketFragment") {
                return@onClick
            }
            imgHome.changeDrawable(R.drawable.ic_pc_home)
            imgTicket.changeDrawable(R.drawable.ic_pc_ticket_selected)
            mainViewModel.navigateToTicket()
        }

        imgHome.onClick {
            if (navController.currentDestination?.label == "PCAdmHomeFragment") {
                return@onClick
            }
            imgHome.changeDrawable(R.drawable.ic_pc_home_selected)
            imgTicket.changeDrawable(R.drawable.ic_pc_ticket)
            mainViewModel.navigateToHome()
        }
        drawMenu()
    }
}

fun PCAdmMenuFragment.drawMenu() {
    binding.apply {
        if (navController.currentDestination?.label == "PCAdmHomeFragment") {
            imgHome.changeDrawable(R.drawable.ic_pc_home_selected)
            imgTicket.changeDrawable(R.drawable.ic_pc_ticket)
        } else if (navController.currentDestination?.label == "PCTicketFragment") {
            imgHome.changeDrawable(R.drawable.ic_pc_home)
            imgTicket.changeDrawable(R.drawable.ic_pc_ticket_selected)
        }
    }
}