package com.boreal.puertocorazon.addevent.ui.menu

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
            addEventViewModel.newEvent.apply {
                if (title.isNotEmpty() && subtitle.isNotEmpty() && description.isNotEmpty()) {
                    checkMain.showView()
                } else {
                    checkMain.invisibleView()
                }
            }
        }

    }

    override fun initView() {
        initElements()
    }
}