package com.boreal.puertocorazon.addevent.ui.details

import com.boreal.commonutils.base.CUBaseFragment
import com.boreal.puertocorazon.addevent.R
import com.boreal.puertocorazon.addevent.databinding.PcDetailsAddEventFragmentBinding

class PCDetailsAddEventFragment : CUBaseFragment<PcDetailsAddEventFragmentBinding>() {

    override fun getLayout() = R.layout.pc_details_add_event_fragment

    override fun initView() {
        initElements()
    }
}