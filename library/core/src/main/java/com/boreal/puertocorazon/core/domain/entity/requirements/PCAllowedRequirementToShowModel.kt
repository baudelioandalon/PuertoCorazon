package com.boreal.puertocorazon.core.domain.entity.requirements

import androidx.annotation.DrawableRes
import com.boreal.puertocorazon.uisystem.R as uiR

data class PCAllowedRequirementToShowModel(
    var selected: Boolean = true,
    @DrawableRes val imageResource: Int = uiR.drawable.ic_pc_old,
    val allowedRequirement: PCRequirementEnum = PCRequirementEnum.OLD
)