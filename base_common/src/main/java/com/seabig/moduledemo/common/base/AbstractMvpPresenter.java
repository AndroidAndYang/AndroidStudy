package com.seabig.moduledemo.common.base;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.seabig.moduledemo.common.callback.ILifeCyclePresenter;
import com.seabig.moduledemo.common.callback.IMvpBaseView;

/**
 * @author YJZ
 *         date 2017/11/17
 *         description 指定绑定的View必须继承自IMvpBaseView4
 */
public abstract class AbstractMvpPresenter<V extends IMvpBaseView> {

    protected V mView;
    protected Context mContext;

    public AbstractMvpPresenter(V v)
    {
        this.mView = v;
        initContext();
    }

    private void initContext()
    {
        if (mView instanceof Fragment)
        {
            mContext = ((Fragment) mView).getActivity();
        } else
        {
            mContext = (Activity) mView;
        }
    }

    /**
     * 绑定V层
     */
    protected void attachMvpView(V view)
    {
        this.mView = view;
    }

    /**
     * 解除绑定V层
     */
    protected void detachMvpView()
    {
        mView = null;
    }

    /**
     * 获取V层
     */
    public V getmMvpView()
    {
        return mView;
    }
}
