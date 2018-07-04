package com.seabig.moduledemo.widget.presenter

import com.android.library.util.ToastUtils
import com.seabig.moduledemo.common.base.AbstractMvpPresenter
import com.seabig.moduledemo.common.callback.ILifeCyclePresenter
import com.seabig.moduledemo.common.ui.widget.LoadingLayout
import com.seabig.moduledemo.widget.presenter.contract.ILifeCycleContract

/**
 * author： YJZ
 * date:  2018/7/4
 * des:
 */

class LifeCyclePresenter(view: ILifeCycleContract.View) : AbstractMvpPresenter<ILifeCycleContract.View>(view), ILifeCyclePresenter {

    override fun onCreate() {
        attachMvpView(mView)
    }

    override fun onStart() {
        ToastUtils.getInstance().showToast(mContext, "請求數據")
        mView.setData()
        mView.setViewStatus(LoadingLayout.SUCCESS)
    }

    override fun onDestroy() {
        detachMvpView()
    }
}
