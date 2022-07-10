package com.boreal.puertocorazon.addevent.ui.details

import android.app.Activity
import androidx.activity.result.contract.ActivityResultContracts
import com.boreal.commonutils.base.CUBaseFragment
import com.boreal.commonutils.extensions.showToast
import com.boreal.puertocorazon.addevent.R
import com.boreal.puertocorazon.addevent.databinding.PcDetailsAddEventFragmentBinding
import com.boreal.puertocorazon.addevent.viewmodel.AddEventViewModel
import com.boreal.puertocorazon.core.component.maputils.AMapUtilityActivity.Companion.NEW_LOCATION
import com.boreal.puertocorazon.core.domain.entity.GeopointModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PCDetailsAddEventFragment : CUBaseFragment<PcDetailsAddEventFragmentBinding>() {

    val viewModel: AddEventViewModel by sharedViewModel()

    val responseLocation =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val address = result.data?.getParcelableExtra<GeopointModel>(NEW_LOCATION)
                address?.let {
                    viewModel.locationSelected = it
                    binding.tvAddress.setText(address.addressName)
                }
            }else{
                showToast("No se seleccionó una ubicación")
            }
        }

    override fun getLayout() = R.layout.pc_details_add_event_fragment

    override fun initView() {
        initElements()
    }
}