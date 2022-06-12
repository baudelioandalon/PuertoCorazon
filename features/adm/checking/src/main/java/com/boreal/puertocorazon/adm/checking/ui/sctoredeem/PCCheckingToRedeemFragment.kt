package com.boreal.puertocorazon.adm.checking.ui.sctoredeem

import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import com.boreal.commonutils.base.CUBaseFragment
import com.boreal.commonutils.extensions.*
import com.boreal.commonutils.utils.GAdapter
import com.boreal.puertocorazon.adm.checking.R
import com.boreal.puertocorazon.adm.checking.databinding.PcCheckingToRedeemFragmentBinding
import com.boreal.puertocorazon.adm.checking.ui.detailticket.PCShowDetailTicket
import com.boreal.puertocorazon.core.domain.entity.AFirestoreStatusRequest
import com.boreal.puertocorazon.core.domain.entity.payment.PCPackageTicketModel
import com.boreal.puertocorazon.core.utils.corefirestore.errorhandler.CUFirestoreErrorEnum
import com.boreal.puertocorazon.core.viewmodel.PCMainViewModel
import com.boreal.puertocorazon.uisystem.databinding.PcRedeemItemBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PCCheckingToRedeemFragment :
    CUBaseFragment<PcCheckingToRedeemFragmentBinding>() {

    val mainViewModel: PCMainViewModel by sharedViewModel()

    val adapterRecyclerToRedeem by lazy {
        GAdapter<PcRedeemItemBinding, PCPackageTicketModel>(
            R.layout.pc_redeem_item,
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
                    containerTicketToRedeem.onClick {
                        PCShowDetailTicket(
                            event = mainViewModel.getEventSelected(),
                            ticket = model
                        ).show(getSupportFragmentManager(), "detail_ticket")
                    }
                    model.apply {
                        if (isPackage) {
                            imgTypeTicket.changeDrawable(R.drawable.ic_pc_package_ticket)
                            tvNamePackage.text = namePackage
                            if (countChild > 0) {

                                val childAvailable = (countChild - getAttendedChild()).toInt()
                                tvCountChildren.text = "$childAvailable Niño${
                                    if (childAvailable > 1) {
                                        "s"
                                    } else {
                                        ""
                                    }
                                }"
                            } else {
                                tvCountChildren.hideView()
                            }
                            if (countAdult > 0) {

                                val adultAvailable = (countAdult - getAttendedAdult()).toInt()
                                tvCountAdults.text = "$adultAvailable Adulto${
                                    if (adultAvailable > 1) {
                                        "s"
                                    } else {
                                        ""
                                    }
                                }"
                            } else {
                                tvCountAdults.hideView()
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
        )
    }

    override fun getLayout() = R.layout.pc_checking_to_redeem_fragment

    override fun initView() {
        initElements()
    }

    override fun initObservers() {
        mainViewModel.ticketListByEvent.observe(viewLifecycleOwner) {
            it?.let {
                binding.recyclerViewToRedeem.setLoading(it.status.ordinal)
                when (it.status) {
                    AFirestoreStatusRequest.LOADING -> {
                    }
                    AFirestoreStatusRequest.SUCCESS, AFirestoreStatusRequest.FAILURE -> {
                        it.failure?.let { errorResult ->
                            if (errorResult == CUFirestoreErrorEnum.ERROR_PERMISSION_DENIED) {
                                mainViewModel.signOutUser()
                            }
                            showToast(errorResult.messageError)
                            return@observe
                        }
                        loadRecyclerEvent(it.response!!)
                    }
                }
            }
        }
    }
}