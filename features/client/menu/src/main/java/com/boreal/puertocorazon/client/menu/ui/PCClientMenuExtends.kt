package com.boreal.puertocorazon.client.menu.ui

import androidx.navigation.fragment.findNavController
import com.boreal.commonutils.extensions.changeDrawable
import com.boreal.commonutils.extensions.hideView
import com.boreal.commonutils.extensions.onClick
import com.boreal.commonutils.extensions.showView
import com.boreal.puertocorazon.client.home.R

fun PCClientMenuFragment.initElements() {
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

        imgTicket.onClick {
            imgHome.changeDrawable(R.drawable.ic_pc_home)
            imgTicket.changeDrawable(R.drawable.ic_pc_ticket_selected)
            mainViewModel.navigateToTicket()
        }

        imgHome.onClick {
            imgHome.changeDrawable(R.drawable.ic_pc_home_selected)
            imgTicket.changeDrawable(R.drawable.ic_pc_ticket)
            mainViewModel.navigateToHome()
        }

    }
}