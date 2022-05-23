package com.boreal.puertocorazon.ticket.ui.showqr

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import com.boreal.commonutils.extensions.onClick
import com.boreal.commonutils.extensions.showImageViewer
import com.boreal.commonutils.utils.GAdapter
import com.boreal.puertocorazon.core.domain.entity.payment.PCPackageTicketModel
import com.boreal.puertocorazon.core.utils.bottomfragment.ABaseBottomSheetDialogFragment
import com.boreal.puertocorazon.ticket.R
import com.boreal.puertocorazon.ticket.databinding.PcQrItemBinding
import com.boreal.puertocorazon.ticket.databinding.PcShowQrBottomFragmentBinding
import com.boreal.puertocorazon.ticket.viewmodel.ShowTicketsViewModel

class PCShowQrTickets(
    val listTickets: List<PCPackageTicketModel> = arrayListOf()
) : ABaseBottomSheetDialogFragment<PcShowQrBottomFragmentBinding>() {

    val showTicketViewModel: ShowTicketsViewModel by viewModels()

    val adapterRecyclerQrs by lazy {
        GAdapter<PcQrItemBinding, PCPackageTicketModel>(
            R.layout.pc_qr_item,
            AsyncDifferConfig.Builder(object : DiffUtil.ItemCallback<PCPackageTicketModel>() {
                override fun areItemsTheSame(
                    oldItem: PCPackageTicketModel,
                    newItem: PCPackageTicketModel
                ) = oldItem == newItem

                override fun areContentsTheSame(
                    oldItem: PCPackageTicketModel,
                    newItem: PCPackageTicketModel
                ) = oldItem == newItem

            }).build(),
            holderCallback = { bindingElement, model, list, adapter, position ->
//                binding.customModel = model.userData
                bindingElement.apply {
                    imgQr.alpha = if (model.isUsed()) {
                        0.5f
                    } else {
                        1f
                    }
                    imgQr.generateQr(model.idTicket, 1200, 1200)
                    imgQr.onClick {
                        showImageViewer(listOf(model.idTicket.generateQr()))
                    }
                }
            }
        )
    }

    override fun getLayout() = R.layout.pc_show_qr_bottom_fragment

    override fun initView() {
        initElements()
    }
}