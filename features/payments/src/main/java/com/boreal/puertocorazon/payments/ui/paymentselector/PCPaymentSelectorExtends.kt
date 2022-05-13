package com.boreal.puertocorazon.payments.ui.paymentselector

import androidx.lifecycle.lifecycleScope
import com.boreal.commonutils.extensions.onClick
import com.boreal.puertocorazon.core.domain.entity.payment.PCTypePayment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun PCPaymentSelector.initElements() {
    binding.apply {

        btnCard.onClick {
            paymentSelected = PCTypePayment.CARD
        }

        btnOxxoPay.onClick {
            lifecycleScope.launch {
                tvErrorMessage.text = "Metodo deshabilitado temporalmente"
                delay(3000)
                tvErrorMessage.text = ""
            }
        }
        btnContinuePayment.onClick {
            if (paymentSelected == PCTypePayment.NONE) {
                lifecycleScope.launch {
                    tvErrorMessage.text = "Selecciona un metodo de pago"
                    delay(3000)
                    tvErrorMessage.text = ""
                }
            } else {
                typePaymentSelected.invoke(paymentSelected)
                closeFragment()
            }
        }
        btnBack.onClick {
            closeFragment()
        }
    }
}