package com.boreal.puertocorazon.addevent.ui.menu

import android.net.Uri
import com.boreal.commonutils.base.CUBaseFragment
import com.boreal.commonutils.extensions.invisibleView
import com.boreal.commonutils.extensions.showView
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
                if (getEventTitle().isNotEmpty() && getEventSubtitle().isNotEmpty() && getEventDescription().isNotEmpty()) {
                    checkMain.showView()
                } else {
                    checkMain.invisibleView()
                }

                if (getGallery().isNotEmpty() && getMainImage() != Uri.EMPTY) {
                    checkGallery.showView()
                } else {
                    checkGallery.invisibleView()
                }
                if (isPriceAdultValid()) {
                    checkPackages.showView()
                } else {
                    checkPackages.invisibleView()
                }
                if (requirementsChanged) {
                    checkRequirements.showView()
                } else {
                    checkRequirements.invisibleView()
                }
                if (isScheduleValid()) {
                    checkDetails.showView()
                } else {
                    checkDetails.invisibleView()
                }
            }
        }

    }

    override fun initView() {
        initElements()
    }
}