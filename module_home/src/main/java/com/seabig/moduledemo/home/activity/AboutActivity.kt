package com.seabig.moduledemo.home.activity

import android.os.Build
import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.widget.Toolbar
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.seabig.moduledemo.common.base.BaseActivity
import com.seabig.moduledemo.common.datamgr.AppConstant
import com.seabig.moduledemo.common.util.ActivityUtils
import com.seabig.moduledemo.common.util.StringUtils
import com.seabig.moduledemo.common.util.SystemUtils
import com.seabig.moduledemo.home.BuildConfig
import com.seabig.moduledemo.home.R

/**
 * author： YJZ
 * date:  2018/6/23
 * des: about pager
 */

class AboutActivity : BaseActivity(), View.OnClickListener {

    private val version = BuildConfig.VERSION_NAME + "-build-" + BuildConfig.VERSION_CODE

    override fun onSettingUpContentViewResourceID(): Int {
        return R.layout.home_activity_about
    }

    override fun onSettingUpView() {
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener({ onBackPressed() })

        val collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar) as CollapsingToolbarLayout
        collapsingToolbarLayout.title = "Yang"

        findViewById(R.id.btn_open_source_url).setOnClickListener(this)
        findViewById(R.id.btn_about_author).setOnClickListener(this)
        findViewById(R.id.btn_advice_feedback).setOnClickListener(this)
        findViewById(R.id.btn_open_source_license).setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        when (v!!.id) {
            // Githup
            R.id.btn_open_source_url -> {
                val bundle = Bundle()
                bundle.putString(AppConstant.WEBVIEW_TITLE, "AndroidAndYang")
                bundle.putString(AppConstant.WEBVIEW_URL, getStringByResId(R.string.open_source_url_content))
                ARouter.getInstance().build("/common/activity/webView").withBundle("bundle", bundle).navigation()
            }

            // Blog
            R.id.btn_about_author -> {
                val bundle = Bundle()
                bundle.putString(AppConstant.WEBVIEW_TITLE, "AndroidAndYang")
                bundle.putString(AppConstant.WEBVIEW_URL, getStringByResId(R.string.about_author_content))
                ARouter.getInstance().build("/common/activity/webView").withBundle("bundle", bundle).navigation()
            }

            // 意见反馈
            R.id.btn_advice_feedback -> {
                SystemUtils.openEmail(this,
                        "yjz0607@gmail.com",
                        "来自 AndroidStudy-$version 的客户端反馈",
                        StringUtils.buffer("设备信息：Android ",Build.VERSION.RELEASE, Build.MANUFACTURER," - ",Build.MODEL))
            }

            // 源代码许可
            R.id.btn_open_source_license -> {
                ActivityUtils.startActivity(this,LicenseActivity::class.java)
            }
        }
    }
}
