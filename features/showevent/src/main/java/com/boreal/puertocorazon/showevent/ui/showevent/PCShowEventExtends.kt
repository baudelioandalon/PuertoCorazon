package com.boreal.puertocorazon.showevent.ui.showevent

import androidx.viewpager.widget.ViewPager
import com.boreal.commonutils.extensions.changeTextColor
import com.boreal.commonutils.extensions.invisibleView
import com.boreal.commonutils.extensions.setOnSingleClickListener
import com.boreal.commonutils.extensions.showView
import com.boreal.puertocorazon.showevent.R
import com.boreal.puertocorazon.showevent.ui.sedescription.PCShowEventDescriptionFragment
import com.boreal.puertocorazon.showevent.ui.segallery.PCShowEventGalleryFragment
import com.boreal.puertocorazon.showevent.ui.sepackages.PCShowEventPackagesFragment

fun PCShowEventFragment.initElements() {
    binding.apply {
        navigation()
        btnClose.setOnSingleClickListener {
            requireActivity().onBackPressed()
        }

        btnDescription.setOnSingleClickListener {
            changingTitleTheme(
                PCShowEventFragmentEnum.DESCRIPTION.position,
                true
            )
        }
        btnGallery.setOnSingleClickListener {
            changingTitleTheme(
                PCShowEventFragmentEnum.GALLERY.position,
                true
            )
        }
        btnPackages.setOnSingleClickListener {
            changingTitleTheme(
                PCShowEventFragmentEnum.PACKAGES.position,
                true
            )
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