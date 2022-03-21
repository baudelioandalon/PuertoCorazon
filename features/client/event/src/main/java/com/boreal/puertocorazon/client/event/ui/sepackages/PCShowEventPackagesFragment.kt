package com.boreal.puertocorazon.client.event.ui.sepackages

import android.os.Bundle
import com.boreal.commonutils.base.CUBaseFragment
import com.boreal.puertocorazon.client.event.R
import com.boreal.puertocorazon.client.event.databinding.PcShowEventPackagesFragmentBinding

class PCShowEventPackagesFragment :
    CUBaseFragment<PcShowEventPackagesFragmentBinding>() {

    //    , PCShowEventPackagesViewModel
    override fun getLayout() = R.layout.pc_show_event_packages_fragment

    override fun initDependency(savedInstanceState: Bundle?) {
    }

    override fun initObservers() {
    }

    override fun initView() {
        initElements()
    }
}