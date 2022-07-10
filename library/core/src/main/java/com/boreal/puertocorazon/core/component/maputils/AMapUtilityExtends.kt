package com.boreal.puertocorazon.core.component.maputils

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.boreal.commonutils.extensions.hideView
import com.boreal.commonutils.extensions.setOnSingleClickListener
import com.boreal.commonutils.extensions.showIf
import com.boreal.commonutils.extensions.showToast
import com.boreal.puertocorazon.core.BuildConfig
import com.boreal.puertocorazon.core.R
import com.boreal.puertocorazon.core.component.maputils.AMapUtilityActivity.Companion.NEW_LOCATION
import com.boreal.puertocorazon.core.component.maputils.core.ApiInterface
import com.boreal.puertocorazon.core.component.maputils.model.ListClass
import com.boreal.puertocorazon.core.component.maputils.model.ModelPlaces
import com.boreal.puertocorazon.core.domain.entity.GeopointModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.*

fun AMapUtilityActivity.initElements() {
    binding.apply {
        // Initialize the SDK
        enableFinishButton(false)
        Places.initialize(applicationContext, BuildConfig.MAPS_API_KEY)
        // Create a new PlacesClient instance
        placesClient = Places.createClient(this@initElements)
        requestLocationPermission()

        KeyboardVisibilityEvent.setEventListener(this@initElements,
            listener = {
                rvPlaces.showIf(it)
            })
        edtSearchAddress.addTextChangedListener {
            if (edtSearchAddress.hasFocus() && KeyboardVisibilityEvent.isKeyboardVisible(this@initElements)) {
                lifecycleScope.launch {
                    isEmptyPlaces.hideView()
                    rvPlaces.adapter = adapter
                    adapter.submitList(getDataPlaces(it.toString()).predictions)
                    enableFinishButton(completeAddress == it.toString())
                    if (completeAddress == it.toString()) {
                        rvPlaces.hideView()
                        hideKeyBoard()
                    }
                }
            }
        }

        btnReady.setOnSingleClickListener {
            Intent().apply {
                if (geoPointSelected != null) {
                    putExtra(NEW_LOCATION, geoPointSelected)
                    setResult(RESULT_OK, this)
                } else {
                    setResult(RESULT_CANCELED, this)
                }
                finish()
            }
        }

        binding.btnMyLocation.setOnSingleClickListener {
            mapUtility.animateCamera(
                CameraUpdateFactory.newLatLngZoom(currentLocation, 17.0f),
                1000,
                null
            )
        }
    }
}

suspend fun AMapUtilityActivity.getDataPlaces(text: String): ModelPlaces =
    withContext(Dispatchers.IO) {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl("https://maps.googleapis.com/maps/api/")
            .build()

        apiInterface = retrofit.create(ApiInterface::class.java)
        apiInterface.getPlace(text, BuildConfig.MAPS_API_KEY)
    }

fun AMapUtilityActivity.createMarkerInLocation() {
    val nameCompleteAddress =
        getCompleteAddressString(currentLocation.latitude, currentLocation.longitude)
    completeAddress = nameCompleteAddress
    enableFinishButton(true)
    createMarker(currentLocation, nameCompleteAddress, moveToLocation = true)
    binding.edtSearchAddress.setText(nameCompleteAddress)
    /**Validamos si es dentro de Mexico*/
    if (geoPointSelected?.countryCode != "MX") {
        showToast("Estás fuera del Pais (México)")
        finish()
    }
}

fun AMapUtilityActivity.enableFinishButton(enableOrNot: Boolean) {
    binding.apply {
        if (enableOrNot) {
            btnDone.backgroundColor =
                ContextCompat.getColor(this@enableFinishButton, R.color.blue_location)
        } else {
            btnDone.backgroundColor =
                ContextCompat.getColor(this@enableFinishButton, R.color.gray_700)
            completeAddress = ""
        }
        btnDone.isEnabled = enableOrNot
    }
}

fun AMapUtilityActivity.createMarker(
    location: LatLng,
    titleText: String,
    removeAllMarkers: Boolean = false,
    moveToLocation: Boolean = false
) {
    val marker = MarkerOptions()
        .position(location)
        .title(titleText)
        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location_marker))
    if (removeAllMarkers) {
        mapUtility.clear()
    }
    mapUtility.addMarker(marker)
    if (moveToLocation) {
        mapUtility.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 17.0f))
    }
}

/**
 * Buscamos direccion
 * */
fun AMapUtilityActivity.searchLocation(location: ListClass, searched: (Address) -> Unit = {}) {
    var addressList: List<Address>? = null
    val geocoder = Geocoder(this)
    try {
        addressList = geocoder.getFromLocationName(location.description, 1)
    } catch (e: IOException) {
        e.printStackTrace()
    }
    if (addressList == null) {
        return
    }
    val address = addressList[0]
    searched(address)
}

/**
 * Convierte las coordenadas de una dirección a texto
 * */
fun AMapUtilityActivity.getCompleteAddressString(latitude: Double, longitude: Double): String {
    var strAdd = ""
    val geocoder = Geocoder(this, Locale.getDefault())
    val addresses: ArrayList<Address>
    try {
        addresses = geocoder.getFromLocation(latitude, longitude, 1) as ArrayList<Address>
        val address: String = addresses[0].getAddressLine(0)

        geoPointSelected = GeopointModel(
            addresses[0].latitude,
            addresses[0].longitude,
            addresses[0].countryCode,
            address
        )
        strAdd = address
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return strAdd
}