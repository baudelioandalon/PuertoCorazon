package com.boreal.puertocorazon.payments.ui.paymentselector

import com.boreal.puertocorazon.core.domain.entity.payment.PCTypePayment
import com.boreal.puertocorazon.core.utils.bottomfragment.ABaseBottomSheetDialogFragment
import com.boreal.puertocorazon.payments.R
import com.boreal.puertocorazon.payments.databinding.PcPayBottomFragmentBinding

class PCPaymentSelector(
    val typePaymentSelected: (PCTypePayment) -> Unit
) : ABaseBottomSheetDialogFragment<PcPayBottomFragmentBinding>(extended = false) {

    var paymentSelected =  PCTypePayment.CARD

    override fun getLayout() = R.layout.pc_pay_bottom_fragment

    override fun initView() {
        initElements()
    }
}