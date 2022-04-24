package com.boreal.puertocorazon.addevent.ui.packages

import com.boreal.puertocorazon.addevent.R
import com.boreal.puertocorazon.addevent.databinding.PcAddPriceBottomFragmentBinding
import com.boreal.puertocorazon.core.utils.bottomfragment.ABaseBottomSheetDialogFragment

class PCAddPriceTicket(
    val title: String,
    val priceTicket: (Int) -> Unit
) : ABaseBottomSheetDialogFragment<PcAddPriceBottomFragmentBinding>(extended = false) {

    override fun getLayout() = R.layout.pc_add_price_bottom_fragment

    override fun initView() {
        initElements()
    }
}