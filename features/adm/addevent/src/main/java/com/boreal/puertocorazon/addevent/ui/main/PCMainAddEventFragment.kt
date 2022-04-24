package com.boreal.puertocorazon.addevent.ui.main

import com.boreal.commonutils.base.CUBaseFragment
import com.boreal.puertocorazon.addevent.R
import com.boreal.puertocorazon.addevent.databinding.PcMainAddEventFragmentBinding
import com.boreal.puertocorazon.addevent.viewmodel.AddEventViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PCMainAddEventFragment : CUBaseFragment<PcMainAddEventFragmentBinding>() {

    val addEventViewModel: AddEventViewModel by sharedViewModel()

    override fun getLayout() = R.layout.pc_main_add_event_fragment

    override fun initView() {
        initElements()
    }
}