package com.boreal.puertocorazon.showevent.ui.sedescription

import android.os.Bundle
import com.boreal.commonutils.base.CUBaseFragment
import com.boreal.puertocorazon.core.viewmodel.PCMainViewModel
import com.boreal.puertocorazon.showevent.R
import com.boreal.puertocorazon.showevent.databinding.PcShowEventDescriptionFragmentBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PCShowEventDescriptionFragment :
    CUBaseFragment<PcShowEventDescriptionFragmentBinding>() {

    val mainViewModel: PCMainViewModel by sharedViewModel()

    override fun getLayout() = R.layout.pc_show_event_description_fragment

    override fun initDependency(savedInstanceState: Bundle?) {
    }

    override fun initObservers() {
    }

    override fun initView() {
        initElements()
    }
}