package com.boreal.puertocorazon.showevent.ui.showevent

import com.boreal.commonutils.base.CUBaseFragment
import com.boreal.puertocorazon.core.viewmodel.PCMainViewModel
import com.boreal.puertocorazon.showevent.R
import com.boreal.puertocorazon.showevent.databinding.PcShowEventFragmentBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PCShowEventFragment :
    CUBaseFragment<PcShowEventFragmentBinding>() {

    val mainViewModel: PCMainViewModel by sharedViewModel()

    override fun getLayout() = R.layout.pc_show_event_fragment

    override fun initView() {
        initElements()
    }
}