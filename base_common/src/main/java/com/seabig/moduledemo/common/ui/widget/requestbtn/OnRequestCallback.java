package com.seabig.moduledemo.common.ui.widget.requestbtn;

/**
 * author:  YJZ
 * date: 2017/10/18
 * description：callback when you can request or remind request over
 */

public interface OnRequestCallback {
    /**
     * before request
     *
     * @return if do request
     */
    boolean beforeRequest();

    /**
     * do your request
     * <p>
     * 数据请求
     */
    void onRequest();

    /**
     * on request over(after tick)
     * <p>
     * 请求结束（打完√）
     */
    void onFinish(boolean isSuccess);

    interface BeforeRequest {
        boolean beforeRequest();
    }

    interface OnRequest {
        void onRequest();
    }

    interface OnFinish {
        void onFinish(boolean isSuccess);
    }
}
