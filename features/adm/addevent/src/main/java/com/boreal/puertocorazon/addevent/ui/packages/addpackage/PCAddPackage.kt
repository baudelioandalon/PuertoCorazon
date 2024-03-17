package com.boreal.puertocorazon.addevent.ui.packages.addpackage

import com.boreal.puertocorazon.adm.addevent.R
import com.boreal.puertocorazon.adm.addevent.databinding.PcAddPackageBottomFragmentBinding
import com.boreal.puertocorazon.core.domain.entity.event.PCPackageToUploadModel
import com.boreal.puertocorazon.core.utils.bottomfragment.ABaseBottomSheetDialogFragment

class PCAddPackage(
    val list: List<PCPackageToUploadModel>,
    val packageResult: (PCPackageToUploadModel) -> Unit
) : ABaseBottomSheetDialogFragment<PcAddPackageBottomFragmentBinding>(extended = false) {

    override fun getLayout() = R.layout.pc_add_package_bottom_fragment

    override fun initView() {
        initElements()
    }
}