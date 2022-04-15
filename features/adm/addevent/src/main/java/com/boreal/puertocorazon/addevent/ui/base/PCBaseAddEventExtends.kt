package com.boreal.puertocorazon.addevent.ui.base

import androidx.constraintlayout.widget.ConstraintSet
import androidx.navigation.fragment.NavHostFragment
import com.boreal.commonutils.extensions.changeDrawable
import com.boreal.commonutils.extensions.hideView
import com.boreal.commonutils.extensions.setOnSingleClickListener
import com.boreal.commonutils.extensions.showView
import com.boreal.puertocorazon.addevent.R


fun PCBaseAddEventFragment.initElements() {
    binding.apply {
        navigation()
        btnBack.setOnSingleClickListener {
            onFragmentBackPressed()
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
                    imgBack.changeDrawable(R.drawable.ic_pc_close)
                    containerPhoto.hideView()
                    navigationAddEvent.showView()
                    btnDeleteImage.hideView()
                    tvTitleEvent.hideView()
                    tvSubtitle.hideView()
                    resetConstraint()
                }
                R.id.PCMainAddEventFragment -> {
                    changeTitle("Principal")
                    imgBack.changeDrawable(R.drawable.ic_pc_left_arrow)
                    resetConstraint()
                }
                R.id.PCGalleryAddEventFragment -> {
                    changeTitle("Galeria")
                    imgBack.changeDrawable(R.drawable.ic_pc_left_arrow)
                    containerPhoto.showView()
                    btnDeleteImage.showView()
                    tvTitleEvent.showView()
                    tvSubtitle.showView()
                    changeConstraint()
                }
                R.id.PCPackagesAddEventFragment -> {
                    changeTitle("Paquetes")
                    imgBack.changeDrawable(R.drawable.ic_pc_left_arrow)
                    containerPhoto.showView()
                    btnDeleteImage.showView()
                    tvTitleEvent.showView()
                    tvSubtitle.showView()
                    changeConstraint()
                }
                R.id.PCDetailsAddEventFragment -> {
                    changeTitle("Detalles")
                    imgBack.changeDrawable(R.drawable.ic_pc_left_arrow)
                    resetConstraint()
                }
                R.id.PCRequirementsAddEventFragment -> {
                    changeTitle(getString(R.string.requirements_text))
                    imgBack.changeDrawable(R.drawable.ic_pc_left_arrow)
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