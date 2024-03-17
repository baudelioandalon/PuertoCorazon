package com.boreal.puertocorazon.adm.checking.ui.scredeem

import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import com.boreal.commonutils.base.CUBaseFragment
import com.boreal.commonutils.extensions.*
import com.boreal.commonutils.utils.GAdapter
import com.boreal.puertocorazon.adm.checking.R
import com.boreal.puertocorazon.uisystem.R as uiR
import com.boreal.commonutils.R as commonR
import com.boreal.puertocorazon.adm.checking.databinding.PcCheckingRedeemFragmentBinding
import com.boreal.puertocorazon.core.domain.entity.AFirestoreStatusRequest
import com.boreal.puertocorazon.core.domain.entity.payment.PCPackageTicketModel
import com.boreal.puertocorazon.core.utils.corefirestore.errorhandler.CUFirestoreErrorEnum
import com.boreal.puertocorazon.core.viewmodel.PCMainViewModel
import com.boreal.puertocorazon.uisystem.databinding.PcRedeemItemBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PCCheckingRedeemFragment :
    CUBaseFragment<PcCheckingRedeemFragmentBinding>() {

    val mainViewModel: PCMainViewModel by sharedViewModel()

    val adapterRecyclerRedeem by lazy {
        GAdapter<PcRedeemItemBinding, PCPackageTicketModel>(
            uiR.layout.pc_redeem_item,
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
                    model.apply {
                        tvNamePackage.changeTextColor(uiR.color.gray_letter)
                        tvNamePackage.changeTypeFace(commonR.font.avenir_medium)
                        imgTypeTicket.changeDrawable(uiR.drawable.ic_pc_single_ticket)
                        tvCountAdults.changeTypeFace(commonR.font.helvetica_neue_bold)
                        tvCountAdults.changeTextColor(uiR.color.gray_letter)
                        tvCountChildren.changeTextColor(uiR.color.gray_letter)
                        tvCountChildren.changeTypeFace(commonR.font.helvetica_neue_bold)
                        if (isPackage) {
                            imgTypeTicket.changeDrawable(uiR.drawable.ic_pc_package_ticket)
                            tvNamePackage.text = "Paquete $namePackage"
                            if (countChild > 0) {
                                val countChildren = countChild.toFloat().toInt()
                                tvCountChildren.text = "$countChildren Niño${
                                    if (countChildren > 1) {
                                        "s"
                                    } else {
                                        ""
                                    }
                                }"
                            } else {
                                tvCountChildren.hideView()
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

                            } else {
                                tvCountAdults.hideView()
                            }
                            if (isPackageUsed()) {
                                tvCountAdults.changeTextColor(uiR.color.gray_letter)
                                tvCountChildren.changeTextColor(uiR.color.gray_letter)
                            }
                        } else {
                            tvNamePackage.text = "Boleto"
                            tvCountAdults.text = if (countAdult > countChild) {
                                "Adulto"
                            } else {
                                "Niño / a"
                            }
                        }
                    }
                }
            }
        )
    }

    override fun getLayout() = R.layout.pc_checking_redeem_fragment

    override fun initView() {
        initElements()
    }

    override fun initObservers() {
        mainViewModel.ticketListByEvent.observe(viewLifecycleOwner) {
            it?.let {
                binding.recyclerViewRedeem.setLoading(it.status.ordinal)
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

                    AFirestoreStatusRequest.NONE -> {}
                }
            }
        }
    }
}