package com.boreal.puertocorazon.addevent.ui.packages

import com.boreal.commonutils.base.CUBaseFragment
import com.boreal.puertocorazon.addevent.R
import com.boreal.puertocorazon.addevent.databinding.PcGalleryAddEventFragmentBinding
import com.boreal.puertocorazon.addevent.databinding.PcMainAddEventFragmentBinding
import com.boreal.puertocorazon.addevent.databinding.PcPackagesAddEventFragmentBinding

class PCPackagesAddEventFragment : CUBaseFragment<PcPackagesAddEventFragmentBinding>() {

    override fun getLayout() = R.layout.pc_packages_add_event_fragment

    override fun initView() {
        initElements()
    }
}