package com.boreal.puertocorazon.core.domain.entity.requirements

import androidx.annotation.DrawableRes
import com.boreal.puertocorazon.core.R

data class PCAllowedRequirementModel(
    var selected: Boolean = false,
    @DrawableRes val imageResource: Int = R.drawable.ic_pc_old,
    val allowedRequirement: PCRequirementEnum = PCRequirementEnum.OLD
)
