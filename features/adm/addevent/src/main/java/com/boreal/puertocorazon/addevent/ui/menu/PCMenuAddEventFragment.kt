package com.boreal.puertocorazon.addevent.ui.menu

import android.net.Uri
import com.boreal.commonutils.base.CUBaseFragment
import com.boreal.commonutils.extensions.notInvisibleIf
import com.boreal.puertocorazon.addevent.R
import com.boreal.puertocorazon.addevent.databinding.PcMenuAddEventFragmentBinding
import com.boreal.puertocorazon.addevent.viewmodel.AddEventViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PCMenuAddEventFragment : CUBaseFragment<PcMenuAddEventFragmentBinding>() {

    val addEventViewModel: AddEventViewModel by sharedViewModel()

    override fun getLayout() = R.layout.pc_menu_add_event_fragment

    override fun initObservers() {
        binding.apply {
            addEventViewModel.apply {
                checkMain.notInvisibleIf(getEventTitle().isNotEmpty() && getEventSubtitle().isNotEmpty() && getEventDescription().isNotEmpty())
                checkGallery.notInvisibleIf(getGallery().isNotEmpty() && getMainImage() != Uri.EMPTY)
                checkPackages.notInvisibleIf(isPriceAdultValid())
                checkDetails.notInvisibleIf(isScheduleValid())
                checkRequirements.notInvisibleIf(requirementsChanged)
            }
        }
    }

    override fun initView() {
        initElements()
    }
}