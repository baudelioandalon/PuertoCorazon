package com.boreal.puertocorazon.generic.ui.fragments.sepackages

import android.os.Bundle
import com.boreal.commonutils.base.CUBaseFragment
import com.boreal.puertocorazon.R
import com.boreal.puertocorazon.databinding.PcHomeFragmentBinding
import com.boreal.puertocorazon.databinding.PcShowEventPackagesFragmentBinding

class PCShowEventPackagesFragment :
    CUBaseFragment<PcShowEventPackagesFragmentBinding, PCShowEventPackagesViewModel>(PCShowEventPackagesViewModel::class) {

    override fun getLayout() = R.layout.pc_show_event_packages_fragment

    override fun initDependency(savedInstanceState: Bundle?) {
    }

    override fun initObservers() {
    }

    override fun initView() {
        initElements()
    }
}