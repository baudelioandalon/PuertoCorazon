package com.boreal.puertocorazon.payments.ui.addcard

import com.boreal.commonutils.base.CUBaseFragment
import com.boreal.puertocorazon.core.viewmodel.PCMainViewModel
import com.boreal.puertocorazon.payments.R
import com.boreal.puertocorazon.payments.databinding.PcAddCardFragmentBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PCAddCardFragment : CUBaseFragment<PcAddCardFragmentBinding>() {

    val mainViewModel: PCMainViewModel by sharedViewModel()

    override fun getLayout() = R.layout.pc_add_card_fragment

    override fun initView() {
        initElements()
    }
}