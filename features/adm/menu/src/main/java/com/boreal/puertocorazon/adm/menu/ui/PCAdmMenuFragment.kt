package com.boreal.puertocorazon.adm.menu.ui

import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.boreal.commonutils.base.CUBaseFragment
import com.boreal.puertocorazon.adm.menu.R
import com.boreal.puertocorazon.adm.menu.databinding.PcAdmMenuFragmentBinding
import com.boreal.puertocorazon.core.viewmodel.PCMainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PCAdmMenuFragment :
    CUBaseFragment<PcAdmMenuFragmentBinding>() {

    lateinit var navController: NavController
    val mainViewModel: PCMainViewModel by sharedViewModel()

    override fun initObservers() {

        mainViewModel.eventSelected.observe(viewLifecycleOwner) {
            it?.let {
                if (it.idEvent != "NONE") {
                    findNavController().navigate(R.id.pc_show_event_graph)
                }
            }
        }

    }

    override fun getLayout() = R.layout.pc_adm_menu_fragment

    override fun initView() {
        initElements()
    }

}