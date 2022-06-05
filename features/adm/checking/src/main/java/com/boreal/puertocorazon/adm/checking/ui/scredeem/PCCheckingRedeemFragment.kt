package com.boreal.puertocorazon.adm.checking.ui.scredeem

import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import com.boreal.commonutils.base.CUBaseFragment
import com.boreal.commonutils.extensions.showToast
import com.boreal.commonutils.utils.GAdapter
import com.boreal.puertocorazon.adm.checking.R
import com.boreal.puertocorazon.adm.checking.databinding.PcCheckingRedeemFragmentBinding
import com.boreal.puertocorazon.core.domain.entity.AFirestoreStatusRequest
import com.boreal.puertocorazon.core.domain.entity.event.PCPackageModel
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
                    model.apply {

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
                }
            }
        }
    }
}