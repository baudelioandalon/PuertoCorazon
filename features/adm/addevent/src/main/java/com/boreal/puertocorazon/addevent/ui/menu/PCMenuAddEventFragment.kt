package com.boreal.puertocorazon.addevent.ui.menu

import com.boreal.commonutils.base.CUBaseFragment
import com.boreal.puertocorazon.addevent.R
import com.boreal.puertocorazon.addevent.databinding.PcMainAddEventFragmentBinding
import com.boreal.puertocorazon.addevent.databinding.PcMenuAddEventFragmentBinding

class PCMenuAddEventFragment : CUBaseFragment<PcMenuAddEventFragmentBinding>() {

    override fun getLayout() = R.layout.pc_menu_add_event_fragment

    override fun initView() {
        initElements()
    }
}