package com.boreal.puertocorazon.generic.ui.fragments.sedescription

import android.os.Bundle
import com.boreal.commonutils.base.CUBaseFragment
import com.boreal.puertocorazon.R
import com.boreal.puertocorazon.databinding.PcShowEventDescriptionFragmentBinding

class PCShowEventDescriptionFragment :
    CUBaseFragment<PcShowEventDescriptionFragmentBinding, PCShowEventDescriptionViewModel>(
        PCShowEventDescriptionViewModel::class
    ) {

    override fun getLayout() = R.layout.pc_show_event_description_fragment

    override fun initDependency(savedInstanceState: Bundle?) {
    }

    override fun initObservers() {
    }

    override fun initView() {
        initElements()
    }
}