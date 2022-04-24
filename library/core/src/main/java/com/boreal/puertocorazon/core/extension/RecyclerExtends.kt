package com.boreal.puertocorazon.core.extension

import android.content.res.Resources
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.boreal.puertocorazon.core.R

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