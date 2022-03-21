package com.boreal.puertocorazon.client.event.ui.sedescription

import android.os.Bundle
import com.boreal.commonutils.base.CUBaseFragment
import com.boreal.puertocorazon.client.event.R
import com.boreal.puertocorazon.client.event.databinding.PcShowEventDescriptionFragmentBinding

class PCShowEventDescriptionFragment :
    CUBaseFragment<PcShowEventDescriptionFragmentBinding>() {
    //    PCShowEventDescriptionViewModel
    override fun getLayout() = R.layout.pc_show_event_description_fragment

    override fun initDependency(savedInstanceState: Bundle?) {
    }

    override fun initObservers() {
    }

    override fun initView() {
        initElements()
    }
}