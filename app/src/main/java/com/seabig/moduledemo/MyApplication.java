package com.seabig.moduledemo;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.seabig.moduledemo.common.base.BaseApplication;
import com.seabig.moduledemo.common.util.Utils;

/**
 * @author YJZ
 *         date 2018/5/3
 *         description
 */

public class MyApplication extends BaseApplication {

    @Override
    public void onCreate()
    {
        super.onCreate();
        Utils.init(this);
        // ARouter相关
        if (Utils.isAppDebug())
        {
            //开启InstantRun之后，一定要在ARouter.init之前调用openDebug
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(this);
    }

    @Override
    protected void attachBaseContext(Context base)
    {
        super.attachBaseContext(base);
        // dex突破65535的限制
        MultiDex.install(this);
    }
}
