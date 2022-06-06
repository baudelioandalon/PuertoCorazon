package com.boreal.puertocorazon.adm.checking.ui.showchecking

import android.content.Intent
import com.boreal.commonutils.base.CUBaseFragment
import com.boreal.commonutils.extensions.getSupportFragmentManager
import com.boreal.commonutils.extensions.showToast
import com.boreal.puertocorazon.adm.checking.R
import com.boreal.puertocorazon.adm.checking.databinding.PcCheckingShowEventFragmentBinding
import com.boreal.puertocorazon.adm.checking.ui.detailticket.PCShowDetailTicket
import com.boreal.puertocorazon.adm.checking.viewmodel.PCCheckingViewModel
import com.boreal.puertocorazon.core.viewmodel.PCMainViewModel
import com.google.zxing.integration.android.IntentIntegrator
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PCCheckingShowEventFragment :
    CUBaseFragment<PcCheckingShowEventFragmentBinding>() {

    val mainViewModel: PCMainViewModel by sharedViewModel()
    val viewModel: PCCheckingViewModel by sharedViewModel()

    override fun getLayout() = R.layout.pc_checking_show_event_fragment

    override fun initView() {
        initElements()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                showToast("Se canceló el escaneo")
            } else {
                if (mainViewModel.getAllTicketsByEvent().isNotEmpty()) {
                    val ticketFounded = mainViewModel.getAllTicketsByEvent()
                        .find { it.idTicket == result.contents }
                    if (ticketFounded != null) {
                        if (ticketFounded.isNotUsed()) {
                            PCShowDetailTicket(
                                event = mainViewModel.getEventSelected(),
                                ticket = ticketFounded
                            ).show(getSupportFragmentManager(), "detail_ticket")
                        } else {
                            showToast("El ticket ya fue redimido")
                        }
                    } else {
                        showToast("El codigo no es un ticket valido para este evento")
                    }
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

}