package com.boreal.puertocorazon.core.extension

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData

inline fun <reified T> Fragment.observe(liveData: LiveData<T>, crossinline data: (T) -> Unit) {
    liveData.observe(viewLifecycleOwner) {
        it?.let {
            data.invoke(it)
        }
    }
}