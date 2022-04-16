package com.boreal.puertocorazon.showevent.ui.showevent

import android.os.Bundle
import androidx.navigation.NavController
import com.boreal.commonutils.base.CUBaseFragment
import com.boreal.puertocorazon.showevent.R
import com.boreal.puertocorazon.showevent.databinding.PcShowEventFragmentBinding

class PCShowEventFragment :
    CUBaseFragment<PcShowEventFragmentBinding>() {
    //    PCShowEventViewModel

    lateinit var navController: NavController

    override fun getLayout() = R.layout.pc_show_event_fragment

    override fun initDependency(savedInstanceState: Bundle?) {
    }

    override fun initObservers() {
    }

    override fun initView() {
        initElements()
    }
}