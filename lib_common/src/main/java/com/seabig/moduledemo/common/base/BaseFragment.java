package com.seabig.moduledemo.common.base;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seabig.moduledemo.common.util.NetworkUtils;
import com.seabig.moduledemo.common.util.ToastUtils;

/**
 * @author YJZ
 *         Description: BaseFragment
 */
public abstract class BaseFragment extends Fragment {

    /**
     * 根view
     */
    protected View mRootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        onCreateSet();

        if (!onDelaySettingUp())
        {
            onBeforeSettingUpView(savedInstanceState);
        }

        if (mRootView == null)
        {
            mRootView = inflater.inflate(onSettingUpContentViewResourceID(), container, false);
        }

        if (!onDelaySettingUp())
        {
            onSettingUpView();
            onSettingUpData();
            onSettingUpListener();
        }

        return mRootView;
    }

    protected void onCreateSet()
    {

    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
    }

    /**
     * 由子类初始化布局的资源ID
     *
     * @return xml Resource ID
     */
    protected abstract int onSettingUpContentViewResourceID();

    /**
     * 由子类来对View进行设置
     */
    protected abstract void onSettingUpView();

    /**
     * 由子类来对监听器进行设置
     */
    protected void onSettingUpListener()
    {
    }

    /**
     * 由子类来对数据进行设置
     */
    protected void onSettingUpData()
    {
    }

    /**
     * 是否延时加载Setting配置
     */
    protected boolean onDelaySettingUp()
    {
        return false;
    }

    /**
     * 用于在初始化View之前做一些事
     */
    protected void onBeforeSettingUpView(Bundle savedInstanceState)
    {
    }

    /**
     * 重载来替代findViewById
     *
     * @param id  控件ID
     * @param <T> View
     * @return 具体的控件
     */
    protected <T extends View> T findViewById(int id)
    {
        return (T) mRootView.findViewById(id);
    }

    /**
     * 在ViewGroup中获取子View
     *
     * @param id   控件ID
     * @param view ViewGroup
     * @param <T>  View
     * @return 具体的控件
     */
    protected <T extends View> T findViewById(View view, int id)
    {
        return (T) view.findViewById(id);
    }

    /**
     * 获取网络状态
     *
     * @return is connect
     */
    protected boolean isNetConnected()
    {
        return NetworkUtils.isConnected(getActivity());
    }

    /**
     * 显示Toast提示框
     *
     * @param msg 提示消息
     */
    protected void showToast(String msg)
    {
        ToastUtils.getInstance().showToast(getActivity(), msg);
    }

    /**
     * 统一从资源ID读取颜色函数
     *
     * @param resId resource id
     * @return resource
     */
    public int getColorByResId(@ColorRes int resId)
    {
        return ContextCompat.getColor(getContext(), resId);
    }

    /**
     * 统一从资源ID读取文本字符串函数
     *
     * @param resId resource id
     * @return resource
     */
    public String getStringByResId(@StringRes int resId)
    {
        return getResources().getString(resId);
    }

    /**
     * 统一从资源ID读取Drawable资源
     *
     * @param resId resource id
     * @return resource
     */
    public Drawable getDrawableByResId(@DrawableRes int resId)
    {
        return ContextCompat.getDrawable(getContext(), resId);
    }

    /**
     * 获取默认字符串值
     *
     * @param value    显示的值
     * @param defValue 默认显示字符
     */
    public String getDefValue(String value, String defValue)
    {
        return TextUtils.isEmpty(value) ? defValue : value;
    }
}
