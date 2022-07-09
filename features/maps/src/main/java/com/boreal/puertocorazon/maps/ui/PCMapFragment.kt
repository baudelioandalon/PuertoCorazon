package com.boreal.puertocorazon.maps.ui

import com.boreal.commonutils.base.CUBaseFragment
import com.boreal.commonutils.extensions.showToast
import com.boreal.puertocorazon.core.domain.entity.AFirestoreStatusRequest
import com.boreal.puertocorazon.core.domain.entity.event.PCEventModel
import com.boreal.puertocorazon.core.utils.corefirestore.errorhandler.CUFirestoreErrorEnum
import com.boreal.puertocorazon.core.viewmodel.PCMainViewModel
import com.boreal.puertocorazon.maps.R
import com.boreal.puertocorazon.maps.databinding.PcMapFragmentBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class PCMapFragment : CUBaseFragment<PcMapFragmentBinding>(), OnMapReadyCallback {

    val mainViewModel: PCMainViewModel by sharedViewModel()
    private lateinit var map: GoogleMap

    override fun getLayout() = R.layout.pc_map_fragment

    override fun initView() {
        initElements()
        createFragmentMap()
    }

    private fun createFragmentMap() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.fragmentMap) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        mainViewModel.eventList.observe(viewLifecycleOwner) {
            it?.let {
                when (it.status) {
                    AFirestoreStatusRequest.LOADING -> {
//                        showProgress()
                        //binding.recyclerClientHomeEvents.setLoading(AFirestoreStatusRequest.LOADING.ordinal)
                    }
                    AFirestoreStatusRequest.SUCCESS, AFirestoreStatusRequest.FAILURE -> {
//                        hideProgressBarCustom()
                        it.failure?.let { errorResult ->
                            if (errorResult == CUFirestoreErrorEnum.ERROR_PERMISSION_DENIED) {
                                mainViewModel.signOutUser()
                            }
                            showToast(errorResult.messageError)
                            return@observe
                        }
                        createMarket(it.response)

                    }
                }
            }
        }
    }

    private fun createMarket(response: List<PCEventModel>?) {

        response?.forEach {
            val coordenadas = LatLng(
                it.place.latitude.toFloat().toDouble(),
                it.place.longitude.toFloat().toDouble()
            )
            val marker = MarkerOptions().position(coordenadas).title(it.title)
            map.addMarker(marker)
            map.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    coordenadas,
                    9f
                ),//coordanadas a mostrar y Cuanto zoom realizara
                4000, null
            )
        }

    }

}