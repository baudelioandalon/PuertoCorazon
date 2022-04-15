package com.boreal.puertocorazon.addevent.ui.requirements

import com.boreal.commonutils.extensions.resDrawableArray
import com.boreal.puertocorazon.addevent.R
import com.boreal.puertocorazon.core.domain.entity.requirements.PCAllowedRequirementModel
import com.boreal.puertocorazon.core.utils.Constants.allowedAccesoriesList
import com.boreal.puertocorazon.core.utils.Constants.allowedClothesList
import com.boreal.puertocorazon.core.utils.Constants.allowedPeopleList


fun PCRequirementsAddEventFragment.initElements() {
    binding.apply {
        initAdapters()
    }
}

fun PCRequirementsAddEventFragment.initAdapters() {

    adapterAllowedPeople.submitList(with(arrayListOf<PCAllowedRequirementModel>()) {
        R.array.allowed_people_drawables.resDrawableArray(requireContext()) { position, drawableResId ->
            add(
                PCAllowedRequirementModel(
                    allowedRequirement = allowedPeopleList[position],
                    imageResource = drawableResId
                )
            )
        }
        this
    })

    adapterAllowedClothes.submitList(with(arrayListOf<PCAllowedRequirementModel>()) {
        R.array.allowed_clothes_drawables.resDrawableArray(requireContext()) { position, drawableResId ->
            add(
                PCAllowedRequirementModel(
                    allowedRequirement = allowedClothesList[position],
                    imageResource = drawableResId
                )
            )
        }
        this
    })

    adapterAllowedAccesories.submitList(with(arrayListOf<PCAllowedRequirementModel>()) {
        R.array.allowed_accesories_drawables.resDrawableArray(requireContext()) { position, drawableResId ->
            add(
                PCAllowedRequirementModel(
                    allowedRequirement = allowedAccesoriesList[position],
                    imageResource = drawableResId
                )
            )
        }
        this
    })
    binding.apply {
        recyclerViewAllowedPeople.adapter = adapterAllowedPeople
        recyclerViewAllowedClothes.adapter = adapterAllowedClothes
        recyclerViewAllowedAccesories.adapter = adapterAllowedAccesories
    }

}