package com.boreal.puertocorazon.client.event.ui.segallery

import android.os.Bundle
import com.boreal.commonutils.base.CUBaseFragment
import com.boreal.puertocorazon.client.event.R
import com.boreal.puertocorazon.client.event.databinding.PcShowEventGalleryFragmentBinding

class PCShowEventGalleryFragment :
    CUBaseFragment<PcShowEventGalleryFragmentBinding>() {
    //    PCShowEventGalleryViewModel
    override fun getLayout() = R.layout.pc_show_event_gallery_fragment

    override fun initDependency(savedInstanceState: Bundle?) {
    }

    override fun initObservers() {
    }

    override fun initView() {
        initElements()
    }
}