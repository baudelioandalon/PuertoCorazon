package com.boreal.puertocorazon.addevent.ui.base

import androidx.navigation.NavController
import com.boreal.commonutils.base.CUBaseFragment
import com.boreal.puertocorazon.addevent.R
import com.boreal.puertocorazon.addevent.databinding.PcBaseAddEventFragmentBinding

class PCBaseAddEventFragment : CUBaseFragment<PcBaseAddEventFragmentBinding>() {

    lateinit var navController: NavController

    override fun getLayout() = R.layout.pc_base_add_event_fragment

    override fun initView() {
        initElements()
    }
}