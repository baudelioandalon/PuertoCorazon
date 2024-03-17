package com.boreal.puertocorazon.addevent.ui.base

import android.net.Uri
import androidx.constraintlayout.widget.ConstraintSet
import androidx.navigation.fragment.NavHostFragment
import com.boreal.commonutils.extensions.*
import com.boreal.puertocorazon.addevent.R
import com.boreal.puertocorazon.uisystem.R as uiR
import com.boreal.puertocorazon.core.component.bottomsheet.ABottomSheetOptionsImageFragment


fun PCBaseAddEventFragment.initElements() {
    binding.apply {
        navigation()
        btnBack.onClick {
            onFragmentBackPressed()
        }

        if (viewModel.getMainImage() != Uri.EMPTY) {
            imgContainer.setImageURI(viewModel.getMainImage())
            btnDeleteImage.showView()
        } else {
            btnDeleteImage.invisibleView()
        }
        containerPhoto.onClick {
            ABottomSheetOptionsImageFragment { uriReceived ->
                viewModel.setMainImage(uriReceived)
                imgContainer.setImageURI(uriReceived)
                imgContainer.showView()
                tvAdd.invisibleView()
                imageView.invisibleView()
                btnDeleteImage.showView()
                tvTitle.changeTextColor(uiR.color.white)
            }.show(
                requireActivity().supportFragmentManager,
                "imageoption"
            )
        }

        btnDeleteImage.onClick {
            imgContainer.invisibleView()
            imgContainer.setImageURI(Uri.EMPTY)
            viewModel.setMainImage(Uri.EMPTY)
            btnDeleteImage.invisibleView()
            tvAdd.showView()
            imageView.showView()
            tvTitle.changeTextColor(uiR.color.black_700)
        }
    }
}

fun PCBaseAddEventFragment.navigation() {
    binding.apply {
        navController =
            (childFragmentManager.findFragmentById(R.id.navigationAddEvent) as NavHostFragment).navController
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.PCMenuAddEventFragment -> {
                    changeTitle("Nuevo Evento")
                    imgBack.changeDrawable(uiR.drawable.ic_pc_close)
                    containerPhoto.hideView()
                    navigationAddEvent.showView()
                    btnDeleteImage.hideView()
                    tvTitleEvent.hideView()
                    tvSubtitle.hideView()
                    resetConstraint()
                    binding.apply {
                        tvTitleEvent.text = viewModel.getEventTitle()
                        tvSubtitle.text = viewModel.getEventSubtitle()
                    }
                }
                R.id.PCMainAddEventFragment -> {
                    changeTitle("Principal")
                    imgBack.changeDrawable(uiR.drawable.ic_pc_left_arrow)
                    resetConstraint()
                }
                R.id.PCGalleryAddEventFragment -> {
                    changeTitle("Galeria")
                    imgBack.changeDrawable(uiR.drawable.ic_pc_left_arrow)
                    containerPhoto.showView()
                    if (viewModel.getMainImage() != Uri.EMPTY) {
                        imgContainer.setImageURI(viewModel.getMainImage())
                        btnDeleteImage.showView()
                    } else {
                        btnDeleteImage.invisibleView()
                    }
                    tvTitleEvent.showView()
                    tvSubtitle.showView()
                    changeConstraint()
                }
                R.id.PCPackagesAddEventFragment -> {
                    changeTitle("Paquetes")
                    imgBack.changeDrawable(uiR.drawable.ic_pc_left_arrow)
                    containerPhoto.showView()
                    btnDeleteImage.invisibleView()
                    tvTitleEvent.showView()
                    tvSubtitle.showView()
                    changeConstraint()
                }
                R.id.PCDetailsAddEventFragment -> {
                    changeTitle("Detalles")
                    imgBack.changeDrawable(uiR.drawable.ic_pc_left_arrow)
                    resetConstraint()
                }
                R.id.PCRequirementsAddEventFragment -> {
                    changeTitle(getString(uiR.string.requirements_text))
                    imgBack.changeDrawable(uiR.drawable.ic_pc_left_arrow)
                    resetConstraint()
                }
                else -> {

                }
            }
        }
    }

}

fun PCBaseAddEventFragment.changeTitle(title: String) {
    binding.tvTitle.text = title
}

fun PCBaseAddEventFragment.resetConstraint() {
    binding.apply {
        with(ConstraintSet()) {
            clone(containerBase)
            connect(
                R.id.navigationAddEvent,
                ConstraintSet.TOP,
                R.id.btnBack,
                ConstraintSet.BOTTOM,
                0
            )
            applyTo(containerBase)
        }
    }
}

fun PCBaseAddEventFragment.changeConstraint() {
    binding.apply {
        with(ConstraintSet()) {
            clone(containerBase)
            connect(
                R.id.navigationAddEvent,
                ConstraintSet.TOP,
                R.id.tvSubtitle,
                ConstraintSet.BOTTOM,
                24
            )
            applyTo(containerBase)
        }
    }
}