package com.seabig.moduledemo.home;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.seabig.moduledemo.common.base.BaseApplication;
import com.seabig.moduledemo.common.util.LogUtils;

/**
 * @author YJZ
 *         date 2018/5/3
 *         description
 */

public class OneApplication extends BaseApplication {

    @Override
    public void onCreate()
    {
        super.onCreate();
        login();
    }

    /**
     * 在这里模拟登陆，然后拿到sessionId或者Token
     * 这样就能够在组件请求接口了
     */
    private void login()
    {
        GetRequest<String> getRequest = OkGo.get("http://gank.io/api/data/福利/10/1");
        getRequest.execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response)
            {
                LogUtils.e("response.body() = " + response.body());
            }
        });
    }
}
