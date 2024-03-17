package com.boreal.puertocorazon.core.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieDrawable
import com.boreal.commonutils.extensions.hideView
import com.boreal.commonutils.extensions.invisibleView
import com.boreal.commonutils.extensions.itemPercent
import com.boreal.commonutils.extensions.showView
import com.boreal.puertocorazon.uisystem.R as uiR
import com.boreal.puertocorazon.core.R
import com.boreal.puertocorazon.core.databinding.CuRecyclerViewBinding

class CURecyclerView(
    context: Context,
    attrs: AttributeSet? = null,
) : ConstraintLayout(context, attrs) {

    private val binding: CuRecyclerViewBinding =
        CuRecyclerViewBinding.inflate(
            LayoutInflater.from(context),
            this, false
        )

    init {
        val view = binding.root
        val set = ConstraintSet()
        addView(view, 0)
        set.clone(this)
        set.connect(
            view.id, ConstraintSet.TOP, this.id,
            ConstraintSet.TOP, 0
        )
        set.applyTo(this)

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CuRecyclerView,
            0, 0
        ).apply {
            try {
                binding.loadingImage.setAnimation(uiR.raw.last_loading)
                binding.loadingImage.repeatCount = LottieDrawable.INFINITE
                binding.loadingImage.playAnimation()

                binding.recyclerViewInside.layoutManager =
                    LinearLayoutManager(
                        context, LinearLayoutManager.VERTICAL,
                        false
                    )
            } finally {
                recycle()
            }

        }
    }

    fun setLoading(loadingType: Int, visibilityType: Boolean = false) {
        binding.apply {
            when (loadingType) {
                1 -> {
                    if (visibilityType) {
                        recyclerViewInside.invisibleView()
                    } else {
                        recyclerViewInside.hideView()
                    }
                    loadingImage.showView()
                }
                2 -> {
                    loadingImage.hideView()
                    recyclerViewInside.showView()
                }
                3 -> {
                    loadingImage.hideView()
                    if (visibilityType) {
                        recyclerViewInside.invisibleView()
                    } else {
                        recyclerViewInside.hideView()
                    }
                }
                else -> {
                    loadingImage.hideView()
                    recyclerViewInside.showView()
                }
            }
        }

    }

    fun itemPercent(percentageDouble: Double) {
        binding.recyclerViewInside.itemPercent(percentageDouble)
    }

    fun adapter(adapter: RecyclerView.Adapter<*>) {
        binding.recyclerViewInside.adapter = adapter
    }

    private fun setLoadingListener(enumStatus: Int) {
//        setLoading(
//            when (0) {
//                1 -> 1
//                2 -> 2
//                3 -> 3
//                else -> {
//                    0
//                }
//            }
//        )
    }
}