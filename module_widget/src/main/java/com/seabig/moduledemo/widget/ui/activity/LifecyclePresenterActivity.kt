package com.seabig.moduledemo.widget.ui.activity

import android.view.View
import com.seabig.moduledemo.common.base.BaseActivity
import com.seabig.moduledemo.common.ui.widget.LoadingLayout
import com.seabig.moduledemo.widget.R
import com.seabig.moduledemo.widget.presenter.LifeCyclePresenter
import com.seabig.moduledemo.widget.presenter.contract.ILifeCycleContract

/**
 * author： YJZ
 * date:  2018/7/4
 * des:
 */

class LifecyclePresenterActivity : BaseActivity(), ILifeCycleContract.View, LoadingLayout.OnReloadListener {

    override fun onReload(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onNetworkError(view: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onEmpty(view: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    var loadingLayout: LoadingLayout? = null

    override fun onSettingUpContentViewResourceID(): Int {
        return R.layout.widget_activity_lifecycler_presenter
    }

    override fun onSettingUpView() {
        loadingLayout = findViewById(R.id.loading_layout) as LoadingLayout
        val presenter = LifeCyclePresenter(this)
        lifecycle.addObserver(presenter)
    }

    override fun setData() {
        showToast("设置数据")
    }

    override fun setViewStatus(status: Int) {
        setViewStatus(loadingLayout,status,this)
    }
}
