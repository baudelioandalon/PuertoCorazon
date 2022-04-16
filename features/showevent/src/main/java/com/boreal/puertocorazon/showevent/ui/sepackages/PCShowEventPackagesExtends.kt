package com.boreal.puertocorazon.showevent.ui.sepackages

import android.content.res.Resources
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.boreal.commonutils.extensions.itemPercent
import com.boreal.puertocorazon.core.domain.entity.event.PCPackageModel
import com.boreal.puertocorazon.showevent.R

fun PCShowEventPackagesFragment.initElements() {
    binding.apply {
        initAdapter()
    }
}

fun PCShowEventPackagesFragment.initAdapter() {
    adapterRecyclerPackages.submitList(
        arrayListOf(
            PCPackageModel(
                titlePackage = "Familia",
                adult = 2L, child = 1, price = 790L
            ), PCPackageModel(
                titlePackage = "Pareja",
                adult = 2L, price = 600L
            )
        )
    )
    binding.apply {
        mRecyclerPackages.apply {
            addLinearHelper()
            adapter = adapterRecyclerPackages
            smoothScrollToPosition(0)
            scrollToPositionCentered()
            itemPercent(.88)
        }
    }

}


fun RecyclerView.addLinearHelper() = LinearSnapHelper().attachToRecyclerView(this)
fun RecyclerView.scrollToPositionCentered(
    position: Int = 0,
    screenWidth: Int = Resources.getSystem().displayMetrics.widthPixels,
    viewWidth: Int = (Resources.getSystem().displayMetrics.widthPixels - 10).dp(),
    paddingOffset: Int = resources.getDimensionPixelOffset(R.dimen.margin_16)
) {
    val centerOfScreen = (screenWidth - viewWidth) / 2 - paddingOffset
    (layoutManager as LinearLayoutManager).scrollToPositionWithOffset(position, centerOfScreen)
}

fun Int.dp() = (this * Resources.getSystem().displayMetrics.density * 0.5f).toInt()