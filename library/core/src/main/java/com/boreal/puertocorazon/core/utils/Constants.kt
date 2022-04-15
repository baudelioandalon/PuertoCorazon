package com.boreal.puertocorazon.core.utils

import com.boreal.puertocorazon.core.domain.entity.requirements.PCRequirementEnum

object Constants {
    val allowedPeopleList = listOf(
        PCRequirementEnum.OLD,
        PCRequirementEnum.PREGNANT,
        PCRequirementEnum.PETS,
        PCRequirementEnum.CHILDREN,
        PCRequirementEnum.WOMAN,
        PCRequirementEnum.MEN
    )

    val allowedClothesList = listOf(
        PCRequirementEnum.PANTS,
        PCRequirementEnum.DRESS,
        PCRequirementEnum.SHORT,
        PCRequirementEnum.SHIRT,
        PCRequirementEnum.LARGE_SHIRT,
        PCRequirementEnum.HIGH_HEEL_SHOES
    )

    val allowedAccesoriesList = listOf(
        PCRequirementEnum.WATCHER,
        PCRequirementEnum.EAR_RINGS,
        PCRequirementEnum.RINGS,
        PCRequirementEnum.NECKLACE
    )
}