package com.seabig.moduledemo.common.callback;

import android.support.annotation.Keep;

/**
 * @author YJZ 2017/9/20 22:23
 *         des ApplicationDelegate
 */
@Keep
public interface IApplicationDelegate {

    void onCreate();

    void onTerminate();

    void onLowMemory();

    void onTrimMemory(int level);

}
