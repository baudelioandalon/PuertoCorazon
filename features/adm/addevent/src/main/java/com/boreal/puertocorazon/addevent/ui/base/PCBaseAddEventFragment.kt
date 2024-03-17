package com.boreal.puertocorazon.addevent.ui.base

import androidx.navigation.NavController
import com.boreal.commonutils.base.CUBaseFragment
import com.boreal.puertocorazon.addevent.viewmodel.AddEventViewModel
import com.boreal.puertocorazon.adm.addevent.R
import com.boreal.puertocorazon.adm.addevent.databinding.PcBaseAddEventFragmentBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PCBaseAddEventFragment : CUBaseFragment<PcBaseAddEventFragmentBinding>() {

    lateinit var navController: NavController

    val viewModel: AddEventViewModel by sharedViewModel()

    override fun getLayout() = R.layout.pc_base_add_event_fragment

    override fun initView() {
        initElements()
    }
}