package com.boreal.puertocorazon.adm.checking.ui.showchecking

import com.boreal.commonutils.base.CUBaseFragment
import com.boreal.puertocorazon.adm.checking.R
import com.boreal.puertocorazon.adm.checking.databinding.PcCheckingShowEventFragmentBinding
import com.boreal.puertocorazon.core.viewmodel.PCMainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PCCheckingShowEventFragment :
    CUBaseFragment<PcCheckingShowEventFragmentBinding>() {

    val mainViewModel: PCMainViewModel by sharedViewModel()

    override fun getLayout() = R.layout.pc_checking_show_event_fragment

    override fun initView() {
        initElements()
    }

}