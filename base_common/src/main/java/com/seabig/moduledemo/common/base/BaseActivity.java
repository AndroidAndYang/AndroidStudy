package com.seabig.moduledemo.common.base;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.RawRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.seabig.moduledemo.common.R;
import com.seabig.moduledemo.common.ui.widget.LoadingLayout;
import com.seabig.moduledemo.common.util.ActivityContainer;
import com.seabig.moduledemo.common.util.LoadingLayoutUtil;
import com.seabig.moduledemo.common.util.NetworkUtils;
import com.seabig.moduledemo.common.util.ToastUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author YJZ
 *         date: 2017/12/11
 *         description：基类Activity 封装了一些自己常用的方法，供子类调用
 */

public abstract class BaseActivity extends AppCompatActivity {

    /**
     * 没有ToolBar时状态栏颜色
     */
    private int mStatusColor = R.color.status_bar_color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (onSettingFullScreen()) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        super.onCreate(savedInstanceState);
        ActivityContainer.getInstance().addActivity(this);
        onCreateSet();
        setContentView(onSettingUpContentViewResourceID());

        Window window = getWindow();
        // 判断是否有Toolbar,并将状态栏与标题栏颜色一致
        if (onSettingFullScreenAndStatusText()) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
                //透明状态栏
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                //透明导航栏
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                ViewGroup contentView = (ViewGroup) findViewById(android.R.id.content);
                // 状态栏
                View childAt = contentView.getChildAt(0);
                if (childAt != null) {
                /*
                 这里fitsSystemWindows是什么意思呢?相信很多人会有疑问?
                 注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 预留出系统 View 的空间.
                 看我们代码流程,首先是设置了FLAG_TRANSLUCENT_STATUS 将状态栏设置为透明的状态
                 fitsSystemWindows代表的是:当设置SystemBar(包含StatusBar&NavigationBar)透明之后,
                 将fitsSystemWindows至为true,则还是为SystemBar预留空间,当设置为false的时候,就是不为SystemBar预留空间
                 现在这个业务场景是不为StatusBar预留空间,所以我们先将StatusBar设置为透明,然后不预留空间
                 这样就实现了我们的功能,全屏但是不隐藏statusBar文字
                 */
                    // 设置成false可以把图片订到状态栏中
                    childAt.setFitsSystemWindows(true);
                }

                if (isSetStatusBarColor()) {
                    //给StatusBar着色
                    View view = new View(this);
                    view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight()));
                    view.setBackgroundColor(ContextCompat.getColor(this, getStatusColor(mStatusColor)));
                    contentView.addView(view);
                }
            }
        } else {
            // 状态栏与标题栏颜色保持一致，并兼容到Android4.4以下
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(ContextCompat.getColor(this, R.color.main));
            } else {
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                ViewGroup systemContent = (ViewGroup) findViewById(android.R.id.content);
                // 添加一个空白的view（StatusBar）
                View statusBarView = new View(this);
                ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight());
                statusBarView.setBackgroundColor(ContextCompat.getColor(this, R.color.main));
                systemContent.getChildAt(0).setFitsSystemWindows(false);
                systemContent.addView(statusBarView, 0, lp);
            }
        }
        onSettingUpView();
        onSettingUpData();
    }

    /**
     * 获取状态栏高度
     *
     * @return 状态栏高度
     */
    public int getStatusBarHeight() {
        int statusBarHeight = -1;
        //获取status_bar_height资源的ID
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    /**
     * 全屏,但保留状态栏文字
     */
    protected boolean onSettingFullScreenAndStatusText() {
        return false;
    }

    /**
     * 是否 给顶部状态栏绘制一个空白的view
     */
    public boolean isSetStatusBarColor() {
        return true;
    }

    protected void onCreateSet() {
    }

    /**
     * 是否为全屏显示
     */
    protected boolean onSettingFullScreen() {
        return false;
    }

    /**
     * 由子类初始化布局的资源ID
     *
     * @return 布局XML
     */
    protected abstract int onSettingUpContentViewResourceID();

    /**
     * 初始化操作
     */
    protected abstract void onSettingUpView();

    /**
     * 初始化数据
     */
    protected void onSettingUpData() {

    }

    /**
     * 显示Toast提示框
     *
     * @param msg 提示消息
     */
    protected void showToast(String msg) {
        ToastUtils.getInstance().showToast(this, msg);
    }

    /**
     * 获取网络状态
     */
    protected boolean isNetConnected() {
        return NetworkUtils.isConnected(this);
    }

    /**
     * 统一从资源ID读取颜色函数
     *
     * @param resId color id
     * @return int
     */
    protected int getColorByResId(@ColorRes int resId) {
        return ContextCompat.getColor(getApplicationContext(), resId);
    }

    /**
     * 统一从资源ID读取文本字符串函数
     *
     * @param resId string id
     * @return String
     */
    protected String getStringByResId(@StringRes int resId) {
        return getApplicationContext().getString(resId);
    }

    /**
     * 统一从资源ID读取Drawable资源
     *
     * @param resId drawable id
     * @return drawable
     */
    protected Drawable getDrawableByResId(@DrawableRes int resId) {
        return ContextCompat.getDrawable(getApplicationContext(), resId);
    }

    /**
     * 获取默认字符串值
     *
     * @param value    显示的值
     * @param defValue 默认显示字符
     */
    public String getDefValue(String value, String defValue) {
        return TextUtils.isEmpty(value) ? defValue : value;
    }

    /**
     * 获取Raw文件夹下的资源
     *
     * @param rawId raw文件夹下的资源id
     * @return string
     */
    public static String getRawString(@NonNull Context context, @RawRes int rawId) throws IOException {
        InputStream is = context.getResources().openRawResource(rawId);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        sb.deleteCharAt(sb.length() - 1);
        reader.close();
        return sb.toString();
    }

    public int getStatusColor(int mStatusColor) {
        return mStatusColor;
    }

    protected void setViewStatus(LoadingLayout loadingLayout, int status, LoadingLayout.OnReloadListener listener)
    {
        switch (status)
        {
            case LoadingLayout.NO_NETWORK:
                LoadingLayoutUtil.setStatusViewOrListener(loadingLayout, LoadingLayout.NO_NETWORK, listener);
                break;

            case LoadingLayout.LOADING:
                LoadingLayoutUtil.setStatusViewOrListener(loadingLayout, LoadingLayout.LOADING);
                break;

            case LoadingLayout.EMPTY:
                LoadingLayoutUtil.setStatusViewOrListener(loadingLayout, LoadingLayout.EMPTY, listener);
                break;

            case LoadingLayout.SUCCESS:
                LoadingLayoutUtil.setStatusViewOrListener(loadingLayout, LoadingLayout.SUCCESS);
                break;

            case LoadingLayout.ERROR:
                LoadingLayoutUtil.setStatusViewOrListener(loadingLayout, LoadingLayout.ERROR, listener);
                break;

            default:
                break;
        }
    }


    public void initToolBar(Toolbar toolbar, String title) {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            if (!TextUtils.isEmpty(title)) {
                toolbar.setTitle(title);
            }

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityContainer.getInstance().removeActivity(this);
        onBaseDestroy();
    }

    public void onBaseDestroy() {

    }
}
