package com.boreal.puertocorazon.addevent.ui.main

import com.boreal.commonutils.base.CUBaseFragment
import com.boreal.puertocorazon.addevent.R
import com.boreal.puertocorazon.addevent.databinding.PcBaseAddEventFragmentBinding
import com.boreal.puertocorazon.addevent.databinding.PcMainAddEventFragmentBinding

class PCMainAddEventFragment : CUBaseFragment<PcMainAddEventFragmentBinding>() {

    override fun getLayout() = R.layout.pc_main_add_event_fragment

    override fun initView() {
        initElements()
    }
}