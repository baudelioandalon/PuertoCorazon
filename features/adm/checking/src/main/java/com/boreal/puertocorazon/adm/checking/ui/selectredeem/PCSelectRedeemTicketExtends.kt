package com.boreal.puertocorazon.adm.checking.ui.selectredeem

import com.boreal.commonutils.dialogs.blurdialog.CUBlurDialogBinding
import com.boreal.commonutils.extensions.getSupportFragmentManager
import com.boreal.commonutils.extensions.onClick
import com.boreal.puertocorazon.adm.checking.R
import com.boreal.puertocorazon.core.domain.entity.ticket.PCAttendedItem
import com.boreal.puertocorazon.uisystem.databinding.PcQuestionDialogBinding

fun PCSelectRedeemTicket.initElements() {
    binding.apply {
        btnToRedeem.onClick {
            CUBlurDialogBinding<PcQuestionDialogBinding>(
                layout = R.layout.pc_question_dialog,
                callback = { binding, dialogBlur ->
                    binding.apply {
                        txtTitle.text = "¿Redimir boletos?"
                        txtMessage.text = "Si continua, los boletos serán\n" +
                                "marcados como redimidos"
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
                cancelable = false
            ).also {
                it.show(getSupportFragmentManager(), "dialog_question")
            }
        }
        btnCancel.onClick {
            closeFragment()
        }

        btnBack.onClick {
            closeFragment()
        }
        createList()
    }
}

fun PCSelectRedeemTicket.createList() {
    /**
     * FILL NOT_ATTENDED_ADULT
     */
    val listToShow = arrayListOf<PCAttendedItem>()
    val countToAttendedAdult = (ticketSelect.countAdult - ticketSelect.attendedAdult).toInt()
    if (countToAttendedAdult > 0) {
        repeat(countToAttendedAdult) {
            with(listToShow) {
                add(
                    PCAttendedItem(
                        ticketType = "ADULT"
                    )
                )
            }
        }
    }


    /**
     * FILL NOT_ATTENDED_CHILD
     */
    val countToAttendedChild = (ticketSelect.countChild - ticketSelect.attendedChild).toInt()
    if (countToAttendedChild > 0) {
        repeat(countToAttendedChild) {
            with(listToShow) {
                add(
                    PCAttendedItem(ticketType = "CHILD")
                )
            }
        }
    }

    binding.recyclerViewSelectedItems.adapter = adapterAttendedItems
    adapterAttendedItems.submitList(listToShow)
}