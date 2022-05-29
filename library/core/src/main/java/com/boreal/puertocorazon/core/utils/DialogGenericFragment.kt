package com.boreal.puertocorazon.core.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment

class DialogGenericFragment<T : ViewDataBinding> constructor(
    @LayoutRes val layout: Int,
    val bindingView: (T, DialogFragment) -> Unit
) : DialogFragment() {

    lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(
            STYLE_NO_TITLE,
            com.google.android.material.R.style.Widget_MaterialComponents_MaterialCalendar_Fullscreen
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, layout, container, false) as T
        initView()
        return binding.root
    }

    private fun initView() {
        bindingView(binding, this)
    }

}