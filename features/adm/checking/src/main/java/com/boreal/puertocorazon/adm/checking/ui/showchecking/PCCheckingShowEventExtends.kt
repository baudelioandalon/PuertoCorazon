package com.boreal.puertocorazon.adm.checking.ui.showchecking

import androidx.viewpager.widget.ViewPager
import com.boreal.commonutils.extensions.changeTextColor
import com.boreal.commonutils.extensions.invisibleView
import com.boreal.commonutils.extensions.onClick
import com.boreal.commonutils.extensions.showView
import com.boreal.puertocorazon.adm.checking.R
import com.boreal.puertocorazon.adm.checking.entity.PCCheckingEventFragmentEnum
import com.boreal.puertocorazon.adm.checking.ui.scdetails.PCCheckingDetailsFragment
import com.boreal.puertocorazon.adm.checking.ui.scredeem.PCCheckingRedeemFragment
import com.boreal.puertocorazon.adm.checking.ui.sctoredeem.PCCheckingToRedeemFragment
import com.boreal.puertocorazon.core.component.tabadapter.MenuBottomAdapter
import com.google.zxing.integration.android.IntentIntegrator

fun PCCheckingShowEventFragment.initElements() {
    binding.apply {
        navigation()
        mainViewModel.requestTicketsByEvent()

        btnClose.onClick {
            onFragmentBackPressed(true)
        }

        btnToRedeem.onClick {
            changingTitleTheme(
                PCCheckingEventFragmentEnum.TO_REDEEM.ordinal,
                true
            )
        }
        btnRedeem.onClick {
            changingTitleTheme(
                PCCheckingEventFragmentEnum.REDEEM.ordinal,
                true
            )
        }
        btnDetails.onClick {
            changingTitleTheme(
                PCCheckingEventFragmentEnum.DETAILS.ordinal,
                true
            )
        }

        btnScan.onClick {
            val integrator = IntentIntegrator.forSupportFragment(this@initElements)
            integrator.setOrientationLocked(false)
            integrator.setPrompt("Escaneo de ticket QR")
            integrator.setBeepEnabled(true)
            integrator.setOrientationLocked(true)
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES)
            integrator.initiateScan()
        }
    }
    fillData()
}

fun PCCheckingShowEventFragment.fillData() {
    binding.apply {
        mainViewModel.getEventSelected().apply {
            mainImage = mainImageUrl
            txtTitle.text = title
            txtSubtitle.text = subtitle
        }
    }
}

fun PCCheckingShowEventFragment.navigation() {
    binding.apply {

        val menuAdapter =
            MenuBottomAdapter(
                arrayListOf(
                    PCCheckingToRedeemFragment(),
                    PCCheckingRedeemFragment(),
                    PCCheckingDetailsFragment()
                ), childFragmentManager
            ) //inicializando adapter
        containerCheckingPager.adapter = menuAdapter

        containerCheckingPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
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

fun PCCheckingShowEventFragment.changingTitleTheme(position: Int, buttonAction: Boolean = false) {
    binding.apply {
        if (buttonAction) {
            containerCheckingPager.currentItem = position
        }
        when (position) {
            PCCheckingEventFragmentEnum.TO_REDEEM.ordinal -> {
                bottomLineDescription.showView()
                titleDescription.changeTextColor(R.color.black_700)
                bottomLineGallery.invisibleView()
                titleGallery.changeTextColor(R.color.gray_viewpager)
                bottomLinePackages.invisibleView()
                titlePackages.changeTextColor(R.color.gray_viewpager)
            }
            PCCheckingEventFragmentEnum.REDEEM.ordinal -> {
                bottomLineGallery.showView()
                titleGallery.changeTextColor(R.color.black_700)
                bottomLineDescription.invisibleView()
                titleDescription.changeTextColor(R.color.gray_viewpager)
                bottomLinePackages.invisibleView()
                titlePackages.changeTextColor(R.color.gray_viewpager)
            }
            PCCheckingEventFragmentEnum.DETAILS.ordinal -> {
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