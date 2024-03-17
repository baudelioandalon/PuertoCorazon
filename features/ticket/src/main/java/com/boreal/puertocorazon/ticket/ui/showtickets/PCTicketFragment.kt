package com.boreal.puertocorazon.ticket.ui.showtickets

import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import com.boreal.commonutils.base.CUBaseFragment
import com.boreal.commonutils.extensions.getSupportFragmentManager
import com.boreal.commonutils.extensions.onClick
import com.boreal.commonutils.extensions.showToast
import com.boreal.commonutils.extensions.showView
import com.boreal.commonutils.utils.GAdapter
import com.boreal.puertocorazon.core.domain.entity.AFirestoreStatusRequest
import com.boreal.puertocorazon.core.domain.entity.payment.PCPackageTicketModel
import com.boreal.puertocorazon.core.utils.corefirestore.errorhandler.CUFirestoreErrorEnum
import com.boreal.puertocorazon.core.viewmodel.PCMainViewModel
import com.boreal.puertocorazon.ticket.R
import com.boreal.puertocorazon.uisystem.R as uiR
import com.boreal.puertocorazon.ticket.databinding.PcTicketFragmentBinding
import com.boreal.puertocorazon.ticket.ui.showqr.PCShowQrTickets
import com.boreal.puertocorazon.uisystem.databinding.PcItemTicketBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PCTicketFragment :
    CUBaseFragment<PcTicketFragmentBinding>() {

    val mainViewModel: PCMainViewModel by sharedViewModel()

    val adapterRecyclerTicketsEvents by lazy {
        GAdapter<PcItemTicketBinding, PCPackageTicketModel>(
            uiR.layout.pc_item_ticket,
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
                bindingElement.apply {
                    nameEvent = model.nameEvent
                    imageEvent = model.imageEvent
                    btnShowTickets.onClick {
                        val ticketsFiltered = mainViewModel.getAllTicketsClientFiltered(model)
                        if (ticketsFiltered.isEmpty()) {
                            showToast("No se pudieron filtrar tus tickets")
                            //Analitycs
                            return@onClick
                        }
                        PCShowQrTickets(model).show(
                            getSupportFragmentManager(),
                            "odmod"
                        )
                    }
                    if (model.isPackage) {
                        tvNamePackage.showView()
                        tvNamePackage.text = model.namePackage

                        tvCountAdults.showView()
                        tvCountAdults.text = "${model.countAdult} x Boletos adulto"
                        tvCountElement.text = "${model.countAdult}x"

                        tvCountChildren.showView()
                        tvCountChildren.text = "${model.countChild} x Boleto niños / as"
                        tvCountElement.text = "${model.countItem}x"
                    } else {
                        model.countChild
                        model.countAdult
                        if (model.countAdult != 0L) {
                            tvCountAdults.showView()
                            tvCountAdults.text = "Boleto adulto"
                            tvCountElement.text = "${model.countAdult}x"
                        } else {
                            tvCountChildren.showView()
                            tvCountChildren.text = "Boleto niño / a"
                            tvCountElement.text = "${model.countChild}x"
                        }
                    }
                }
            }
        )
    }

    override fun getLayout() = R.layout.pc_ticket_fragment

    override fun initObservers() {

        mainViewModel.ticketListByClient.observe(viewLifecycleOwner) {
            it?.let {
                when (it.status) {
                    AFirestoreStatusRequest.LOADING -> {
//                        showProgress()
                        binding.recyclerClientTicketsEvents.setLoading(AFirestoreStatusRequest.LOADING.ordinal)
                    }
                    AFirestoreStatusRequest.SUCCESS, AFirestoreStatusRequest.FAILURE -> {
                        binding.recyclerClientTicketsEvents.setLoading(it.status.ordinal)
//                        hideProgressBarCustom()
                        it.failure?.let { errorResult ->
                            if (errorResult == CUFirestoreErrorEnum.ERROR_PERMISSION_DENIED) {
                                mainViewModel.signOutUser()
                            }
                            showToast(errorResult.messageError)
                            return@observe
                        }
                        loadRecyclerEvent(it.response!!)
                    }

                    AFirestoreStatusRequest.NONE -> {}
                }
            }
        }
    }

    override fun initView() {
        initElements()
    }

}