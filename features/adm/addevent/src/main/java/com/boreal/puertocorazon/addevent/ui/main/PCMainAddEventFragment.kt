package com.boreal.puertocorazon.addevent.ui.main

import com.boreal.commonutils.base.CUBaseFragment
import com.boreal.puertocorazon.addevent.viewmodel.AddEventViewModel
import com.boreal.puertocorazon.adm.addevent.R
import com.boreal.puertocorazon.adm.addevent.databinding.PcMainAddEventFragmentBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PCMainAddEventFragment : CUBaseFragment<PcMainAddEventFragmentBinding>() {

    val addEventViewModel: AddEventViewModel by sharedViewModel()

    override fun getLayout() = R.layout.pc_main_add_event_fragment

    override fun initView() {
        initElements()
    }
}