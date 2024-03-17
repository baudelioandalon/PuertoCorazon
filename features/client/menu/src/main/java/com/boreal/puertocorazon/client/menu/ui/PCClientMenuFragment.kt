package com.boreal.puertocorazon.client.menu.ui

import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.boreal.commonutils.base.CUBaseFragment
import com.boreal.puertocorazon.client.menu.R
import com.boreal.puertocorazon.showevent.R as showEventR
import com.boreal.puertocorazon.client.menu.databinding.PcClientMenuFragmentBinding
import com.boreal.puertocorazon.core.viewmodel.PCMainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PCClientMenuFragment :
    CUBaseFragment<PcClientMenuFragmentBinding>() {

    lateinit var navController: NavController
    val mainViewModel: PCMainViewModel by sharedViewModel()

    override fun initObservers() {

        mainViewModel.eventSelected.observe(viewLifecycleOwner) {
            it?.let {
                if (it.idEvent != "NONE") {
                    findNavController().navigate(showEventR.id.pc_show_event_graph)
                }
            }
        }

    }

    override fun getLayout() = R.layout.pc_client_menu_fragment

    override fun initView() {
        initElements()
    }

}