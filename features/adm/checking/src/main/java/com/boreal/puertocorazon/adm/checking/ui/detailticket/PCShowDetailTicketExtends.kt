package com.boreal.puertocorazon.adm.checking.ui.detailticket

import androidx.constraintlayout.widget.ConstraintSet
import com.boreal.commonutils.extensions.hideView
import com.boreal.commonutils.extensions.onClick
import com.boreal.puertocorazon.adm.checking.R
import com.boreal.puertocorazon.core.extension.dp
import com.boreal.puertocorazon.core.utils.toFormat

fun PCShowDetailTicket.initElements() {
    binding.apply {
        btnBack.onClick {
            closeFragment()
        }
        event.apply {
            tvTitleEvent.text = title
            imageEvent = mainImageUrl
            txtDateEvent.text = schedule.first().startTime.toFormat()
            if (!ticket.isPackage) {
                btnSelectItems.hideView()
                txtRedeem.text = getString(R.string.to_redeem_text)
                with(ConstraintSet()) {
                    clone(containerDetailsTicket)
                    connect(
                        R.id.scrollContainerDetailTicket,
                        ConstraintSet.TOP,
                        R.id.tvTitleEvent,
                        ConstraintSet.BOTTOM,
                        100.dp()
                    )
                    connect(
                        R.id.scrollContainerDetailTicket,
                        ConstraintSet.BOTTOM,
                        R.id.btnReady,
                        ConstraintSet.TOP,
                        55
                    )
                    applyTo(containerDetailsTicket)
                }
            }
        }

        btnSelectItems.onClick {

        }
    }
}