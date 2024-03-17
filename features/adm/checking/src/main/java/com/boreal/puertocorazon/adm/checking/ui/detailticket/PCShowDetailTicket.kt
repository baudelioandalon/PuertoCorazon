package com.boreal.puertocorazon.adm.checking.ui.detailticket

import com.boreal.commonutils.extensions.showToast
import com.boreal.puertocorazon.adm.checking.R
import com.boreal.puertocorazon.adm.checking.databinding.PcShowDetailTicketBottomFragmentBinding
import com.boreal.puertocorazon.adm.checking.viewmodel.PCCheckingViewModel
import com.boreal.puertocorazon.core.domain.entity.AFirestoreStatusRequest
import com.boreal.puertocorazon.core.domain.entity.event.PCEventModel
import com.boreal.puertocorazon.core.domain.entity.payment.PCPackageTicketModel
import com.boreal.puertocorazon.core.utils.bottomfragment.ABaseBottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PCShowDetailTicket(
    val event: PCEventModel,
    val ticket: PCPackageTicketModel
) : ABaseBottomSheetDialogFragment<PcShowDetailTicketBottomFragmentBinding>() {

    val viewModel: PCCheckingViewModel by sharedViewModel()

    override fun getLayout() = R.layout.pc_show_detail_ticket_bottom_fragment

    override fun initView() {
        initElements()
    }

    override fun initObservers() {
        viewModel.updateChecking.observe(viewLifecycleOwner) {
            when (it.status) {
                AFirestoreStatusRequest.LOADING -> {
                    showProgressBarCustom()
                }
                AFirestoreStatusRequest.SUCCESS, AFirestoreStatusRequest.FAILURE -> {
                    hideProgressBarCustom()
                    viewModel.resetViewModel()
                    it.failure?.let { errorResult ->
                        showToast(errorResult.messageError)
                        return@observe
                    }
                    showToast(
                        "El ${
                            if (ticket.isPackage) {
                                "paquete "
                            } else {
                                "boleto "
                            }
                        }se redimio exitosamente"
                    )
                    closeFragment()
                }

                AFirestoreStatusRequest.NONE -> TODO()
            }
        }
    }

}