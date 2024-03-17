package com.boreal.puertocorazon.adm.checking.ui.selectredeem

import com.boreal.commonutils.component.dialogs.blurdialog.CUBlurDialogBinding
import com.boreal.commonutils.extensions.onClick
import com.boreal.commonutils.extensions.showToast
import com.boreal.puertocorazon.uisystem.R as uiR
import com.boreal.puertocorazon.core.constants.NONE
import com.boreal.puertocorazon.core.domain.entity.ticket.PCAttendedItem
import com.boreal.puertocorazon.core.domain.entity.ticket.PCTicketType
import com.boreal.puertocorazon.uisystem.databinding.PcQuestionDialogBinding
import com.google.firebase.Timestamp

fun PCSelectRedeemTicket.initElements() {
    binding.apply {
        btnToRedeem.onClick {
            if (adapterAttendedItems.currentList.count { it.selected } == 0) {
                showToast("No has seleccionado ningún boleto")
                return@onClick
            }
            CUBlurDialogBinding<PcQuestionDialogBinding>(
                layout = uiR.layout.pc_question_dialog,
                callback = { binding, dialogBlur ->
                    binding.apply {
                        txtTitle.text = "¿Redimir boletos?"
                        txtMessage.text = "Si continua, los boletos serán\n" +
                                "marcados como redimidos"
                        txtBtnCancel.text = getString(uiR.string.cancelar)
                        txtBtnContinue.text = "Redimir"
                        btnCancel.onClick {
                            dialogBlur.dismissAllowingStateLoss()
                        }
                        btnContinue.onClick {
                            val mapToAdd: ArrayList<Map<String, Any>> = arrayListOf()
                            val timeNow = Timestamp.now()

                            mapToAdd.addAll(ticketSelect.attendedTime.map {
                                mapOf(
                                    "attendedDate" to it.attendedDate,
                                    "attendedType" to it.attendedType,
                                    "imageSaved" to it.imageSaved,
                                    "ticketType" to it.ticketType
                                )
                            })
                            repeat(adapterAttendedItems.currentList.count { it.selected && it.ticketType == PCTicketType.ADULT.name }) {
                                mapToAdd.add(
                                    mapOf(
                                        "attendedDate" to timeNow,
                                        "attendedType" to true,
                                        "imageSaved" to NONE,
                                        "ticketType" to PCTicketType.ADULT.name
                                    )
                                )
                            }
                            repeat(adapterAttendedItems.currentList.count { it.selected && it.ticketType == PCTicketType.CHILD.name }) {
                                mapToAdd.add(
                                    mapOf(
                                        "attendedDate" to timeNow,
                                        "attendedType" to true,
                                        "imageSaved" to NONE,
                                        "ticketType" to PCTicketType.CHILD.name
                                    )
                                )
                            }
                            viewModel.updateTicket(
                                ticketSelect.idTicket, mapToAdd
                            )
                            dialogBlur.dismissAllowingStateLoss()
                        }
                    }
                },
                cancelable = false
            ).also {
                it.show(requireActivity().supportFragmentManager, "dialog_question_redeem")
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
    val countToAttendedAdult = (ticketSelect.countAdult - ticketSelect.getAttendedAdult()).toInt()
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
    val countToAttendedChild = (ticketSelect.countChild - ticketSelect.getAttendedChild()).toInt()
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