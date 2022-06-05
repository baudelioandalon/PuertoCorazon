package com.boreal.puertocorazon.adm.checking.ui.detailticket

import com.boreal.puertocorazon.adm.checking.R
import com.boreal.puertocorazon.adm.checking.databinding.PcShowDetailTicketBottomFragmentBinding
import com.boreal.puertocorazon.core.domain.entity.event.PCEventModel
import com.boreal.puertocorazon.core.domain.entity.payment.PCPackageTicketModel
import com.boreal.puertocorazon.core.utils.bottomfragment.ABaseBottomSheetDialogFragment

class PCShowDetailTicket(
    val event: PCEventModel,
    val ticket: PCPackageTicketModel
) : ABaseBottomSheetDialogFragment<PcShowDetailTicketBottomFragmentBinding>() {

    override fun getLayout() = R.layout.pc_show_detail_ticket_bottom_fragment

    override fun initView() {
        initElements()
    }

}