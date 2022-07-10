package com.boreal.puertocorazon.core.component.maputils

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import com.boreal.commonutils.base.CUBaseActivity
import com.boreal.commonutils.component.dialogs.blurdialog.CUBlurDialog
import com.boreal.commonutils.component.dialogs.blurdialog.CUTitleModel
import com.boreal.commonutils.component.dialogs.blurdialog.CUTitleType
import com.boreal.commonutils.extensions.removeTilde
import com.boreal.commonutils.extensions.setOnSingleClickListener
import com.boreal.commonutils.extensions.showToast
import com.boreal.commonutils.utils.GAdapter
import com.boreal.puertocorazon.core.R
import com.boreal.puertocorazon.core.component.maputils.core.ApiInterface
import com.boreal.puertocorazon.core.component.maputils.model.ListClass
import com.boreal.puertocorazon.core.databinding.AAddressItemBinding
import com.boreal.puertocorazon.core.databinding.AMapUtilityBinding
import com.boreal.puertocorazon.core.domain.entity.GeopointModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.net.PlacesClient
import kotlinx.coroutines.Job

class AMapUtilityActivity :
    CUBaseActivity<AMapUtilityBinding>(),
    OnMapReadyCallback, GoogleMap.OnMyLocationClickListener {

    lateinit var mapUtility: GoogleMap

    //    var userLocation = Location("")
    lateinit var placesClient: PlacesClient
    lateinit var apiInterface: ApiInterface
    lateinit var currentLocation: LatLng
    var completeAddress = ""
    var geoPointSelected: GeopointModel? = null
    var googlePlacesJob: Job? = null
    /*  val handlerException = CoroutineExceptionHandler { coroutineContext, throwable ->
          Log.e("googlePlacesJob", "$throwable in $coroutineContext")
      }*/

    companion object {
        const val REQUEST_CODE_LOCATION = 0
        const val NEW_LOCATION = "NEW_LOCATION"
    }

    val adapter by lazy {
        GAdapter<AAddressItemBinding, ListClass>(
            R.layout.a_address_item,
            AsyncDifferConfig.Builder(object : DiffUtil.ItemCallback<ListClass>() {
                override fun areItemsTheSame(oldItem: ListClass, newItem: ListClass) =
                    oldItem.description == newItem.description

                override fun areContentsTheSame(oldItem: ListClass, newItem: ListClass) =
                    oldItem == newItem
            }).build(),
            holderCallback = { itemBinding, model, list, adapter, position ->
                itemBinding.apply {
                    txtAddress.text = model.description
                    itemAddress.setOnSingleClickListener {
                        enableFinishButton(true)
                        searchLocation(model) { result ->
                            geoPointSelected = GeopointModel(
                                result.latitude,
                                result.longitude,
                                result.countryCode,
                                result.getAddressLine(0)
                            )
                            createMarker(
                                location = LatLng(result.latitude, result.longitude),
                                titleText = result.getAddressLine(0),
                                removeAllMarkers = true,
                                moveToLocation = true
                            )
                            completeAddress = result.getAddressLine(0).removeTilde()
                            binding.edtSearchAddress.setText(result.getAddressLine(0))
                        }
                    }
                }
            }
        )
    }

    override fun getLayout() = R.layout.a_map_utility

    override fun initView() {
        initElements()
    }

    private fun createFragmentMap() {
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.mapPaymentUtility) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mapUtility = googleMap
        createMarkerInLocation()
        mapUtility.setOnMyLocationClickListener(this)
    }

    private fun isLocationPermissionGranted() = ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    fun requestLocationPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isLocationPermissionGranted()) {
                getUserLocation()
            } else {
                val permissionArray = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
                requestPermissions(permissionArray, REQUEST_CODE_LOCATION)
            }
        } else {
            getUserLocation()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_LOCATION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getUserLocation()
                } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    showLocationPermissionDialog()
                } else {
                    finish()
                }
            }
            else -> {

            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getUserLocation() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                currentLocation = LatLng(location.latitude, location.longitude)
//                userLocation.latitude = location.latitude
//                userLocation.longitude = location.longitude
                createFragmentMap()
            }
        }
    }

    private fun showLocationPermissionDialog() {
        val dialog = CUBlurDialog(
            cuTitleModel = CUTitleModel("Necesitas permiso de ubicación", CUTitleType.WARNING),
            messageGeneric = "Acepta el permiso de ubicación",
            buttonTopText = "Aceptar",
            buttonClicked = {
                requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_CODE_LOCATION
                )
                it.dismiss()
            },
            buttonCancelText = "NO",
            buttonCancelClicked = { finish() },
            cancelable = true
        )
        dialog.showCustom(supportFragmentManager, "Dialog")
    }

    override fun onMyLocationClick(p0: Location) {
        showToast("Estas en ${p0.latitude}, ${p0.longitude}")
    }

}