package com.boreal.puertocorazon.adm.checking.ui.detailticket

import androidx.constraintlayout.widget.ConstraintSet
import com.boreal.commonutils.dialogs.blurdialog.CUBlurDialogBinding
import com.boreal.commonutils.extensions.*
import com.boreal.puertocorazon.adm.checking.R
import com.boreal.puertocorazon.adm.checking.ui.selectredeem.PCSelectRedeemTicket
import com.boreal.puertocorazon.core.extension.dp
import com.boreal.puertocorazon.core.utils.toFormat
import com.boreal.puertocorazon.uisystem.databinding.PcQuestionDialogBinding

fun PCShowDetailTicket.initElements() {
    binding.apply {
        fillData()
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
            PCSelectRedeemTicket(
                ticketSelect = ticket.copy()
            ).show(getSupportFragmentManager(), "select_items")
        }

        btnToRedeem.onClick {
            CUBlurDialogBinding<PcQuestionDialogBinding>(
                layout = R.layout.pc_question_dialog,
                callback = { binding, dialogBlur ->
                    binding.apply {
                        txtTitle.text = "¿Redimir boleto?"
                        txtMessage.text = "Si continua, el boleto será\n" +
                                "marcado como redimido"
                        txtBtnCancel.text = getString(R.string.cancelar)
                        txtBtnContinue.text = "Redimir"
                        btnCancel.onClick {
                            dialogBlur.dismissAllowingStateLoss()
                        }
                        btnContinue.onClick {

                            dialogBlur.dismissAllowingStateLoss()
                        }
                    }
                },
                cancelable = true
            ).also {
                it.show(getSupportFragmentManager(), "dialog_question")
            }
        }
    }
}

@Deprecated(
    replaceWith = ReplaceWith("TODO"),
    message = "Cambiar por customView",
    level = DeprecationLevel.WARNING
)
fun PCShowDetailTicket.fillData() {
    binding.apply {
        ticket.apply {
            txtDateEvent.text = event.schedule.first().startTime.toFormat()
            if (isPackage) {
                imgTypeTicket.changeDrawable(R.drawable.ic_pc_package_ticket)
                tvNamePackage.text = namePackage
                if (countChild > 0) {
                    val countChildren = countChild.toFloat().toInt()
                    tvCountChildren.text = "$countChildren Niño${
                        if (countChildren > 1) {
                            "s"
                        } else {
                            ""
                        }
                    }"
                    val childAvailable = (countChild - attendedChild).toInt()
                    tvCountChildrenAvailable.text = "$childAvailable Niño${
                        if (childAvailable > 1) {
                            "s"
                        } else {
                            ""
                        }
                    }"
                } else {
                    tvCountChildrenAvailable.invisibleView()
                }
                if (countAdult > 0) {
                    val countAdult = countAdult.toFloat().toInt()
                    tvCountAdults.text = "$countAdult Adulto${
                        if (countAdult > 1) {
                            "s"
                        } else {
                            ""
                        }
                    }"
                    val adultAvailable = (countAdult - attendedAdult).toInt()
                    tvCountAdultsAvailable.text = "$adultAvailable Adulto${
                        if (adultAvailable > 1) {
                            "s"
                        } else {
                            ""
                        }
                    }"
                } else {
                    tvCountAdultsAvailable.invisibleView()
                }
                if (isPackageUsed()) {
                    tvCountAdults.changeTextColor(R.color.orange_700)
                    tvCountChildren.changeTextColor(R.color.orange_700)
                }
            } else {
                imgTypeTicket.changeDrawable(R.drawable.ic_pc_single_ticket)
                tvNamePackage.text = "Boleto ${
                    if (countAdult > countChild) {
                        tvCountAdults.text = if (isAdultUsed()) {
                            tvCountAdults.changeTextColor(R.color.orange_700)
                            "Usado"
                        } else {
                            tvCountAdults.changeTextColor(R.color.green_700)
                            "Disponible"
                        }
                        "Adulto"
                    } else {
                        tvCountAdults.text = if (isChildUsed()) {
                            tvCountAdults.changeTextColor(R.color.orange_700)
                            "Usado"
                        } else {
                            tvCountAdults.changeTextColor(R.color.green_700)
                            "Disponible"
                        }
                        "Infantil"
                    }
                }"
            }
        }
    }
}