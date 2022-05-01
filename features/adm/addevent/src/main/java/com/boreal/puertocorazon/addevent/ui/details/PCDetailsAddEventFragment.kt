package com.boreal.puertocorazon.addevent.ui.details

import com.boreal.commonutils.base.CUBaseFragment
import com.boreal.puertocorazon.addevent.R
import com.boreal.puertocorazon.addevent.databinding.PcDetailsAddEventFragmentBinding
import com.boreal.puertocorazon.addevent.viewmodel.AddEventViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PCDetailsAddEventFragment : CUBaseFragment<PcDetailsAddEventFragmentBinding>() {

    val viewModel: AddEventViewModel by sharedViewModel()

    override fun getLayout() = R.layout.pc_details_add_event_fragment

    override fun initView() {
        initElements()
    }
}