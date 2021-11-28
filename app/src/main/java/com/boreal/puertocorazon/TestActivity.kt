package com.boreal.puertocorazon

import com.boreal.commonutils.base.CUBaseActivity
import com.boreal.puertocorazon.databinding.ActivityMainBinding

class TestActivity: CUBaseActivity<ActivityMainBinding,MainViewModel>(MainViewModel::class) {

    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun initDependency() {
    }

    override fun initObservers() {
    }

    override fun initView() {

        initElements()
    }
}