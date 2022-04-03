package com.boreal.puertocorazon.addevent.ui.gallery

import com.boreal.commonutils.base.CUBaseFragment
import com.boreal.puertocorazon.addevent.R
import com.boreal.puertocorazon.addevent.databinding.PcGalleryAddEventFragmentBinding
import com.boreal.puertocorazon.addevent.databinding.PcMainAddEventFragmentBinding

class PCGalleryAddEventFragment : CUBaseFragment<PcGalleryAddEventFragmentBinding>() {

    override fun getLayout() = R.layout.pc_gallery_add_event_fragment

    override fun initView() {
        initElements()
    }
}