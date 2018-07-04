package com.seabig.moduledemo.common.util;

import com.seabig.moduledemo.common.ui.widget.LoadingLayout;

import static com.lzy.okgo.utils.HttpUtils.runOnUiThread;

/**
 * @author  YJZ
 * date: 2017/11/2
 * description：
 */

public class LoadingLayoutUtil {

    /**
     * 对LoadingLayout不同状态下的一个处理
     * @param loadingLayout loadingLayout
     * @param status 状态
     * @param listener 监听
     */
    public static void setStatusViewOrListener(final LoadingLayout loadingLayout, final int status, LoadingLayout.OnReloadListener listener)
    {
        new Thread() {
            @Override
            public void run()
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run()
                    {
                        loadingLayout.setStatus(status);
                    }
                });
            }
        }.start();
        loadingLayout.setOnReloadListener(listener);
    }

    /**
     * 对LoadingLayout不同状态下的一个处理
     */

    public static void setStatusViewOrListener(final LoadingLayout loadingLayout, final int status)
    {
        new Thread() {
            @Override
            public void run()
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run()
                    {
                        loadingLayout.setStatus(status);
                    }
                });
            }
        }.start();
    }
}
