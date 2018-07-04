package com.seabig.moduledemo.widget.presenter.contract

import com.seabig.moduledemo.common.callback.IMvpBaseView

/**
 * author： YJZ
 * date:  2018/7/4
 * des:
 */

interface ILifeCycleContract {

    interface View : IMvpBaseView {
        fun setData();
    }
}
