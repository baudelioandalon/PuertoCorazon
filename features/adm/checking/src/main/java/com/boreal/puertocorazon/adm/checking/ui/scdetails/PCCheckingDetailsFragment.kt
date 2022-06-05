package com.boreal.puertocorazon.adm.checking.ui.scdetails

import com.boreal.commonutils.base.CUBaseFragment
import com.boreal.puertocorazon.adm.checking.R
import com.boreal.puertocorazon.adm.checking.databinding.PcCheckingDetailsFragmentBinding
import com.boreal.puertocorazon.core.viewmodel.PCMainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PCCheckingDetailsFragment :
    CUBaseFragment<PcCheckingDetailsFragmentBinding>() {

    val mainViewModel: PCMainViewModel by sharedViewModel()

    override fun getLayout() = R.layout.pc_checking_details_fragment

    override fun initView() {
        initElements()
    }
}