package com.seabig.moduledemo.common.callback;

/**
 * @author YJZ
 *         date 2017/11/17
 *         description 所有mvpView的父类
 */
public interface IMvpBaseView {

    /**
     * 通过status来控制视图的显示
     *
     * @param status status
     */
    void setViewStatus(int status);

}
