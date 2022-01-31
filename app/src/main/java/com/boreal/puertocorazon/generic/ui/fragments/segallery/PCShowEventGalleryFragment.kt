package com.boreal.puertocorazon.generic.ui.fragments.segallery

import android.os.Bundle
import com.boreal.commonutils.base.CUBaseFragment
import com.boreal.puertocorazon.R
import com.boreal.puertocorazon.databinding.PcHomeFragmentBinding
import com.boreal.puertocorazon.databinding.PcShowEventGalleryFragmentBinding

class PCShowEventGalleryFragment :
    CUBaseFragment<PcShowEventGalleryFragmentBinding, PCShowEventGalleryViewModel>(PCShowEventGalleryViewModel::class) {

    override fun getLayout() = R.layout.pc_show_event_gallery_fragment

    override fun initDependency(savedInstanceState: Bundle?) {
    }

    override fun initObservers() {
    }

    override fun initView() {
        initElements()
    }
}