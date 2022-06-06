package com.boreal.puertocorazon.adm.checking.ui.selectredeem

import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import com.boreal.commonutils.extensions.changeDrawable
import com.boreal.commonutils.extensions.onClick
import com.boreal.commonutils.extensions.showToast
import com.boreal.commonutils.utils.GAdapter
import com.boreal.puertocorazon.adm.checking.R
import com.boreal.puertocorazon.adm.checking.databinding.PcSelectRedeemBottomFragmentBinding
import com.boreal.puertocorazon.adm.checking.viewmodel.PCCheckingViewModel
import com.boreal.puertocorazon.core.domain.entity.AFirestoreStatusRequest
import com.boreal.puertocorazon.core.domain.entity.payment.PCPackageTicketModel
import com.boreal.puertocorazon.core.domain.entity.ticket.PCAttendedItem
import com.boreal.puertocorazon.core.domain.entity.ticket.PCTicketType
import com.boreal.puertocorazon.core.utils.bottomfragment.ABaseBottomSheetDialogFragment
import com.boreal.puertocorazon.core.utils.toFormat
import com.boreal.puertocorazon.uisystem.databinding.PcSelectTicketItemBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PCSelectRedeemTicket(
    val ticketSelect: PCPackageTicketModel
) : ABaseBottomSheetDialogFragment<PcSelectRedeemBottomFragmentBinding>() {

    val viewModel: PCCheckingViewModel by sharedViewModel()

    val adapterAttendedItems by lazy {
        GAdapter<PcSelectTicketItemBinding, PCAttendedItem>(
            R.layout.pc_select_ticket_item,
            AsyncDifferConfig.Builder(object : DiffUtil.ItemCallback<PCAttendedItem>() {
                override fun areItemsTheSame(
                    oldItem: PCAttendedItem,
                    newItem: PCAttendedItem
                ) = oldItem == newItem

                override fun areContentsTheSame(
                    oldItem: PCAttendedItem,
                    newItem: PCAttendedItem
                ) = oldItem == newItem

            }).build(),
            holderCallback = { bindingElement, model, _, adapter, position ->
                bindingElement.apply {
                    model.apply {
                        if (model.ticketType == PCTicketType.ADULT.name) {
                            tvCountAdults.text = "Adulto"
                        } else {
                            tvCountAdults.text = "Niño / a"
                        }
                        if (attendedType) {
                            btnAttendedItem.isEnabled = false
                            tvNamePackage.text = attendedDate.toFormat()
                            imgTypeTicket.changeDrawable(R.drawable.ic_pc_arrow_correct_gray)
                        } else {
                            tvNamePackage.text = "Disponible"
                            if (selected) {
                                imgTypeTicket.changeDrawable(R.drawable.ic_pc_arrow_correct)
                                imgSpacer.changeDrawable(R.drawable.ic_pc_vertical_spacer_blue)
                            } else {
                                imgTypeTicket.changeDrawable(R.drawable.ic_pc_clock_gray)
                                imgSpacer.changeDrawable(R.drawable.ic_pc_vertical_spacer)
                            }
                            btnAttendedItem.onClick {
                                model.selected = !model.selected
                                adapter.notifyItemChanged(position)
                            }

                        }
                    }
                }
            }
        )
    }

    override fun getLayout() = R.layout.pc_select_redeem_bottom_fragment

    override fun initView() {
        initElements()
    }

    override fun initObservers() {
        viewModel.updateChecking.observe(viewLifecycleOwner) {
            when (it.status) {
                AFirestoreStatusRequest.SUCCESS, AFirestoreStatusRequest.FAILURE -> {
                    hideProgressBarCustom()
                    viewModel.resetViewModel()
                    it.failure?.let { errorResult ->
                        showToast(errorResult.messageError)
                        return@observe
                    }
                    showToast(
                        "Los boletos se redimieron exitosamente"
                    )
                    closeFragment()
                }
            }
        }
    }

}