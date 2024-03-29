package com.boreal.puertocorazon.showevent.ui.showevent

import androidx.viewpager.widget.ViewPager
import com.boreal.commonutils.extensions.changeTextColor
import com.boreal.commonutils.extensions.invisibleView
import com.boreal.commonutils.extensions.onClick
import com.boreal.commonutils.extensions.showView
import com.boreal.puertocorazon.core.component.tabadapter.MenuBottomAdapter
import com.boreal.puertocorazon.uisystem.R
import com.boreal.puertocorazon.showevent.entity.PCShowEventFragmentEnum
import com.boreal.puertocorazon.showevent.ui.sedescription.PCShowEventDescriptionFragment
import com.boreal.puertocorazon.showevent.ui.segallery.PCShowEventGalleryFragment
import com.boreal.puertocorazon.showevent.ui.sepackages.PCShowEventPackagesFragment

fun PCShowEventFragment.initElements() {
    binding.apply {
        navigation()
        onBackPressedDispatcher {
            mainViewModel.removeEventSelected()
            onFragmentBackPressed(true)
        }
        btnClose.onClick {
            mainViewModel.removeEventSelected()
            onFragmentBackPressed(true)
        }

        btnDescription.onClick {
            changingTitleTheme(
                PCShowEventFragmentEnum.DESCRIPTION.position,
                true
            )
        }
        btnGallery.onClick {
            changingTitleTheme(
                PCShowEventFragmentEnum.GALLERY.position,
                true
            )
        }
        btnPackages.onClick {
            changingTitleTheme(
                PCShowEventFragmentEnum.PACKAGES.position,
                true
            )
        }

        btnPayments.onClick {
            mainViewModel.goToPayments()
        }
    }
    fillData()
}

fun PCShowEventFragment.fillData() {
    binding.apply {
        mainViewModel.getEventSelected().apply {
            mainImage = mainImageUrl
            txtTitle.text = title
            txtSubtitle.text = subtitle
        }
    }
}

fun PCShowEventFragment.navigation() {
    binding.apply {

        val menuAdapter =
            MenuBottomAdapter(
                arrayListOf(
                    PCShowEventDescriptionFragment(),
                    PCShowEventGalleryFragment(),
                    PCShowEventPackagesFragment()
                ), childFragmentManager
            ) //inicializando adapter
        containerShowEvent.adapter = menuAdapter

        containerShowEvent.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                changingTitleTheme(position)
            }

            override fun onPageSelected(position: Int) {}
            override fun onPageScrollStateChanged(state: Int) {}

        })
    }

}

fun PCShowEventFragment.changingTitleTheme(position: Int, buttonAction: Boolean = false) {
    binding.apply {
        if (buttonAction) {
            containerShowEvent.currentItem = position
        }
        when (position) {
            PCShowEventFragmentEnum.DESCRIPTION.position -> {
                bottomLineDescription.showView()
                titleDescription.changeTextColor(R.color.black_700)
                bottomLineGallery.invisibleView()
                titleGallery.changeTextColor(R.color.gray_viewpager)
                bottomLinePackages.invisibleView()
                titlePackages.changeTextColor(R.color.gray_viewpager)
            }
            PCShowEventFragmentEnum.GALLERY.position -> {
                bottomLineGallery.showView()
                titleGallery.changeTextColor(R.color.black_700)
                bottomLineDescription.invisibleView()
                titleDescription.changeTextColor(R.color.gray_viewpager)
                bottomLinePackages.invisibleView()
                titlePackages.changeTextColor(R.color.gray_viewpager)
            }
            PCShowEventFragmentEnum.PACKAGES.position -> {
                bottomLinePackages.showView()
                titlePackages.changeTextColor(R.color.black_700)
                bottomLineDescription.invisibleView()
                titleDescription.changeTextColor(R.color.gray_viewpager)
                bottomLineGallery.invisibleView()
                titleGallery.changeTextColor(R.color.gray_viewpager)
            }
        }
    }

}