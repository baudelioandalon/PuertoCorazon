package com.boreal.puertocorazon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.boreal.puertocorazon.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var mBinding:ActivityMainBinding

    val viewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this,
            R.layout.activity_main)
        setContentView(mBinding.root)
        initElements()

    }



}