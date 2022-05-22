package com.boreal.puertocorazon.ticket.ui.showqr

import com.boreal.puertocorazon.core.utils.bottomfragment.ABaseBottomSheetDialogFragment
import com.boreal.puertocorazon.ticket.R
import com.boreal.puertocorazon.ticket.databinding.PcShowQrBottomFragmentBinding

class PCShowQrTickets :
    ABaseBottomSheetDialogFragment<PcShowQrBottomFragmentBinding>(extended = false) {

    override fun getLayout() = R.layout.pc_show_qr_bottom_fragment

    override fun initView() {
        initElements()
    }
}