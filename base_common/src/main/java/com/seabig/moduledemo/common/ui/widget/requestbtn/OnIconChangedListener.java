package com.seabig.moduledemo.common.ui.widget.requestbtn;

/**
 * author:  YJZ
 * date: 2017/10/18
 * description：当图标改变
 */

public interface OnIconChangedListener {
    /**
     * call this when request finished
     * 请求结束时调用
     * @return if need to do circle and tick是否继续画圈及打钩
     */
    boolean onProgressFinished(RequestIcon icon);

    /**
     * call this when tick finish
     * 打钩结束
     */
    void onTickFinished(RequestIcon icon);
}
