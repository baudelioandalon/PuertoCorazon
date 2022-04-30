package com.boreal.puertocorazon.addevent.ui.requirements

import com.boreal.commonutils.extensions.resDrawableArray
import com.boreal.commonutils.extensions.setOnSingleClickListener
import com.boreal.puertocorazon.addevent.R
import com.boreal.puertocorazon.core.domain.entity.requirements.PCAllowedRequirementToShowModel
import com.boreal.puertocorazon.core.utils.Constants.allowedAccesoriesList
import com.boreal.puertocorazon.core.utils.Constants.allowedClothesList
import com.boreal.puertocorazon.core.utils.Constants.allowedPeopleList


fun PCRequirementsAddEventFragment.initElements() {
    binding.apply {

        btnSave.setOnSingleClickListener {
            viewModel.setAllowedAccesories(adapterAllowedAccesories.currentList.filter { it.selected }
                .map { it.allowedRequirement.name })
            viewModel.setAllowedClothing(adapterAllowedClothes.currentList.filter { it.selected }
                .map { it.allowedRequirement.name })
            viewModel.setAllowedPeople(adapterAllowedPeople.currentList.filter { it.selected }
                .map { it.allowedRequirement.name })
            viewModel.requirementsChanged = true
            onFragmentBackPressed()
        }
        initAdapters()
    }
}

fun PCRequirementsAddEventFragment.initAdapters() {
    adapterAllowedPeople.submitList(with(arrayListOf<PCAllowedRequirementToShowModel>()) {
        R.array.allowed_people_drawables.resDrawableArray(requireContext()) { position, drawableResId ->
            add(
                PCAllowedRequirementToShowModel(
                    allowedRequirement = allowedPeopleList[position],
                    imageResource = drawableResId,
                    selected = viewModel.getAllowedPeople()
                        .contains(allowedPeopleList[position].name)
                )
            )
        }
        this
    })

    adapterAllowedClothes.submitList(with(arrayListOf<PCAllowedRequirementToShowModel>()) {
        R.array.allowed_clothes_drawables.resDrawableArray(requireContext()) { position, drawableResId ->
            add(
                PCAllowedRequirementToShowModel(
                    allowedRequirement = allowedClothesList[position],
                    imageResource = drawableResId,
                    selected = viewModel.getAllowedClothing()
                        .contains(allowedClothesList[position].name)
                )
            )
        }
        this
    })

    adapterAllowedAccesories.submitList(with(arrayListOf<PCAllowedRequirementToShowModel>()) {
        R.array.allowed_accesories_drawables.resDrawableArray(requireContext()) { position, drawableResId ->
            add(
                PCAllowedRequirementToShowModel(
                    allowedRequirement = allowedAccesoriesList[position],
                    imageResource = drawableResId,
                    selected = viewModel.getAllowedAccesories()
                        .contains(allowedAccesoriesList[position].name)
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