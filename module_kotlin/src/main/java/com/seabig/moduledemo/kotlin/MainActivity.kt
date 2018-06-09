package com.seabig.moduledemo.kotlin

import android.widget.TextView
import com.seabig.moduledemo.common.base.BaseActivity

/**
 * author： YJZ
 * date:  2018/6/9
 * des: main
 */

class MainActivity : BaseActivity() {

    override fun onSettingUpContentViewResourceID(): Int {
        return R.layout.kotlin_activity_main
    }

    override fun onSettingUpView() {
        val textView = findViewById(R.id.text) as TextView
        textView.text = "Hello Kotlin!"
    }
}
