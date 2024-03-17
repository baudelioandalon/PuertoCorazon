package com.boreal.puertocorazon.addevent.ui.packages.addprice

import com.boreal.puertocorazon.adm.addevent.R
import com.boreal.puertocorazon.adm.addevent.databinding.PcAddPriceBottomFragmentBinding
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