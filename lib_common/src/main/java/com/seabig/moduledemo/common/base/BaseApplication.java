package com.seabig.moduledemo.common.base;

import android.app.Application;

import com.seabig.moduledemo.common.callback.IApplicationDelegate;
import com.seabig.moduledemo.common.util.ClassUtils;

import java.util.List;

/**
 * @author YJZ
 *         date 2018/5/3
 *         description
 */

public class BaseApplication extends Application {

    public static final String ROOT_PACKAGE = "com.seabig.moduledemo";

    private static BaseApplication sInstance;

    private List<IApplicationDelegate> mAppDelegateList;

    public static BaseApplication getIns()
    {
        return sInstance;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        sInstance = this;
        mAppDelegateList = ClassUtils.getObjectsWithInterface(this, IApplicationDelegate.class, ROOT_PACKAGE);
        for (IApplicationDelegate delegate : mAppDelegateList)
        {
            delegate.onCreate();
        }
    }

    @Override
    public void onTerminate()
    {
        super.onTerminate();
        for (IApplicationDelegate delegate : mAppDelegateList)
        {
            delegate.onTerminate();
        }
    }

    @Override
    public void onLowMemory()
    {
        super.onLowMemory();
        for (IApplicationDelegate delegate : mAppDelegateList)
        {
            delegate.onLowMemory();
        }
    }

    @Override
    public void onTrimMemory(int level)
    {
        super.onTrimMemory(level);
        for (IApplicationDelegate delegate : mAppDelegateList)
        {
            delegate.onTrimMemory(level);
        }
    }
}
