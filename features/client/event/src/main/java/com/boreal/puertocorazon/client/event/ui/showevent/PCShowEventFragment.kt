package com.boreal.puertocorazon.client.event.ui.showevent

import android.os.Bundle
import com.boreal.commonutils.base.CUBaseFragment
import com.boreal.puertocorazon.client.event.R
import com.boreal.puertocorazon.client.event.databinding.PcShowEventFragmentBinding

class PCShowEventFragment :
    CUBaseFragment<PcShowEventFragmentBinding>() {
    //    PCShowEventViewModel
    override fun getLayout() = R.layout.pc_show_event_fragment

    override fun initDependency(savedInstanceState: Bundle?) {
    }

    override fun initObservers() {
    }

    override fun initView() {
        initElements()
    }
}