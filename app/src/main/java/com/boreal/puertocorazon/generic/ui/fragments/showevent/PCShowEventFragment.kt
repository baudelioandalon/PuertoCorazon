package com.boreal.puertocorazon.generic.ui.fragments.showevent

import android.os.Bundle
import com.boreal.commonutils.base.CUBaseFragment
import com.boreal.puertocorazon.R
import com.boreal.puertocorazon.databinding.PcHomeFragmentBinding
import com.boreal.puertocorazon.databinding.PcShowEventFragmentBinding

class PCShowEventFragment :
    CUBaseFragment<PcShowEventFragmentBinding, PCShowEventViewModel>(PCShowEventViewModel::class) {

    override fun getLayout() = R.layout.pc_show_event_fragment

    override fun initDependency(savedInstanceState: Bundle?) {
    }

    override fun initObservers() {
    }

    override fun initView() {
        initElements()
    }
}