package com.seabig.moduledemo.home.activity

import android.support.v7.widget.Toolbar
import android.widget.TextView
import com.seabig.moduledemo.common.base.BaseActivity
import com.seabig.moduledemo.home.R

/**
 * author： YJZ
 * date:  2018/6/23
 * des: 开发源代码许可
 */

class LicenseActivity : BaseActivity() {

    override fun onSettingUpContentViewResourceID(): Int {
        return R.layout.home_activity_license
    }

    override fun onSettingUpView() {

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        initToolBar(toolbar, getStringByResId(R.string.open_source_license))

        val tvLicense =  findViewById(R.id.tv_license) as TextView
        tvLicense.text = getRawString(this@LicenseActivity,R.raw.open_source)
    }
}
