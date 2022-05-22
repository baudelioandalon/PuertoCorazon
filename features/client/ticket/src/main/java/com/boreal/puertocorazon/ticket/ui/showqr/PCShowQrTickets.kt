package com.boreal.puertocorazon.ticket.ui.showqr

import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import com.boreal.commonutils.utils.GAdapter
import com.boreal.puertocorazon.core.utils.bottomfragment.ABaseBottomSheetDialogFragment
import com.boreal.puertocorazon.ticket.R
import com.boreal.puertocorazon.ticket.databinding.PcQrItemBinding
import com.boreal.puertocorazon.ticket.databinding.PcShowQrBottomFragmentBinding

class PCShowQrTickets(
    val listTickets: ArrayList<String> = arrayListOf()
) : ABaseBottomSheetDialogFragment<PcShowQrBottomFragmentBinding>(extended = false) {

    val adapterRecyclerQrs by lazy {
        GAdapter<PcQrItemBinding, String>(
            R.layout.pc_qr_item,
            AsyncDifferConfig.Builder(object : DiffUtil.ItemCallback<String>() {
                override fun areItemsTheSame(
                    oldItem: String,
                    newItem: String
                ) = oldItem == newItem

                override fun areContentsTheSame(
                    oldItem: String,
                    newItem: String
                ) = oldItem == newItem

            }).build(),
            holderCallback = { bindingElement, model, list, adapter, position ->
//                binding.customModel = model.userData
                bindingElement.apply {
                    imgQr.generateQr(model)
                }
            }
        )
    }

    override fun getLayout() = R.layout.pc_show_qr_bottom_fragment

    override fun initView() {
        initElements()
    }
}