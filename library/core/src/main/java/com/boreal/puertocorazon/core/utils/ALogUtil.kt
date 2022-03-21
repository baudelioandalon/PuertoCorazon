package com.boreal.puertocorazon.core.utils

import android.util.Log

fun String.log(key: String, error: Boolean = false){
    if(!error){
        Log.i(key,this)
    }else{
        Log.e(key,this)
    }
}