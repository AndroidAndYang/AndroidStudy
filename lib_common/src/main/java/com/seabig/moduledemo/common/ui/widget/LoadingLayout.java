package com.seabig.moduledemo.common.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.seabig.moduledemo.common.R;

/**
 * @author by YJZ
 *         time 2016-03-12
 *         des 各种状态的试图
 */
public class LoadingLayout extends FrameLayout {

    /**
     * 成功
     */
    public final static int SUCCESS = 0;
    /**
     * 数据为空
     */
    public final static int EMPTY = 1;
    /**
     * 加载错误
     */
    public final static int ERROR = 2;
    /**
     * 无网络
     */
    public final static int NO_NETWORK = 3;
    /**
     * 加载中
     */
    public final static int LOADING = 4;
    /**
     * 没有登录
     */
    public final static int NO_LOGIN = 5;
    private int state;
    private Context mContext;
    private View loadingPage;
    private View errorPage;
    private View emptyPage;
    private View networkPage;
    private View noLoginPage;
    private View defineLoadingPage;
    private ImageView errorImg;
    private ImageView emptyImg;
    private LinearLayout emptyLinear;
    private ImageView networkImg;
    private ImageView noLoginImg;
    private TextView errorText;
    private TextView emptyText;
    private TextView networkText;
    private TextView noLoginText;
    private TextView errorReloadBtn;
    private TextView networkReloadBtn;
    private TextView noLoginReloadBtn;
    private View contentView;
    private OnReloadListener listener;
    /**
     * 是否一开始显示ContentView，默认不显示
     */
    private boolean isFirstVisible;
    /**
     * LoadingLayout 配置信息
     */
    public static Config mConfig = new Config();

    public LoadingLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LoadingLayout);
        isFirstVisible = a.getBoolean(R.styleable.LoadingLayout_isFirstVisible, false);
        a.recycle();
    }

    public LoadingLayout(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    public LoadingLayout(Context context)
    {
        super(context);
        this.mContext = context;
    }

    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();
        if (getChildCount() > 1)
        {
            throw new IllegalStateException("LoadingLayout can host only one direct child");
        }
        contentView = this.getChildAt(0);
        if (!isFirstVisible)
        {
            contentView.setVisibility(View.GONE);
        }
        build();
    }

    private void build()
    {
        if (mConfig.loadingView == null)
        {
            loadingPage = LayoutInflater.from(mContext).inflate(mConfig.loadingLayoutId, null);
        } else
        {
            loadingPage = mConfig.loadingView;
        }
        errorPage = LayoutInflater.from(mContext).inflate(R.layout.commont_loading_widget_error_page, null);
        emptyPage = LayoutInflater.from(mContext).inflate(R.layout.common_loading_widget_empty_page, null);
        networkPage = LayoutInflater.from(mContext).inflate(R.layout.commont_loaidng_widget_no_network_page, null);
        noLoginPage = LayoutInflater.from(mContext).inflate(R.layout.commont_loading_widget_no_login_page, null);
        defineLoadingPage = null;

        loadingPage.setBackgroundColor(getColor(mContext, mConfig.backgroundColor));
        errorPage.setBackgroundColor(getColor(mContext, mConfig.backgroundColor));
        emptyPage.setBackgroundColor(getColor(mContext, mConfig.backgroundColor));
        networkPage.setBackgroundColor(getColor(mContext, mConfig.backgroundColor));
        noLoginPage.setBackgroundColor(getColor(mContext, mConfig.backgroundColor));

        errorText = findViewById(errorPage, R.id.error_text);
        emptyText = findViewById(emptyPage, R.id.empty_text);
        networkText = findViewById(networkPage, R.id.no_network_text);
        noLoginText = findViewById(noLoginPage, R.id.no_login_text);

        errorImg = findViewById(errorPage, R.id.error_img);
        emptyLinear = findViewById(emptyPage, R.id.empty_root);
        emptyImg = findViewById(emptyPage, R.id.empty_img);

        networkImg = findViewById(networkPage, R.id.no_network_img);
        noLoginImg = findViewById(noLoginPage, R.id.no_login_img);

        errorReloadBtn = findViewById(errorPage, R.id.error_reload_btn);
        networkReloadBtn = findViewById(networkPage, R.id.no_network_reload_btn);
        noLoginReloadBtn = findViewById(noLoginPage, R.id.no_login_reload_btn);

        errorReloadBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (listener != null)
                {
                    listener.onReload(v);
                }
            }
        });

        networkReloadBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (listener != null)
                {
                    listener.onNetworkError(v);
                }
            }
        });

        emptyLinear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (listener != null)
                {
                    listener.onEmpty(v);
                }
            }
        });

        noLoginReloadBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (listener != null)
                {
                    listener.onReload(v);
                }
            }
        });

        errorText.setText(mConfig.errorStr);
        emptyText.setText(mConfig.emptyStr);
        networkText.setText(mConfig.netWorkStr);
        noLoginText.setText(mConfig.noLoginStr);

        errorText.setTextSize(mConfig.tipTextSize);
        emptyText.setTextSize(mConfig.tipTextSize);
        networkText.setTextSize(mConfig.tipTextSize);
        noLoginText.setTextSize(mConfig.tipTextSize);

        errorText.setTextColor(getColor(mContext, mConfig.tipTextColor));
        emptyText.setTextColor(getColor(mContext, mConfig.tipTextColor));
        networkText.setTextColor(getColor(mContext, mConfig.tipTextColor));
        noLoginText.setTextColor(getColor(mContext, mConfig.tipTextColor));

        errorImg.setImageResource(mConfig.errorImgId);
        emptyImg.setImageResource(mConfig.emptyImgId);
        networkImg.setImageResource(mConfig.networkImgId);
        noLoginImg.setImageResource(mConfig.noLoginImgId);

        errorReloadBtn.setBackgroundResource(mConfig.reloadBtnId);
        networkReloadBtn.setBackgroundResource(mConfig.reloadBtnId);
        noLoginReloadBtn.setBackgroundResource(mConfig.reloadBtnId);

        errorReloadBtn.setText(mConfig.reloadBtnStr);
        networkReloadBtn.setText(mConfig.reloadBtnStr);
        noLoginReloadBtn.setText(mConfig.noLoginBtnStr);

        errorReloadBtn.setTextSize(mConfig.buttonTextSize);
        networkReloadBtn.setTextSize(mConfig.buttonTextSize);
        noLoginReloadBtn.setTextSize(mConfig.buttonTextSize);

        errorReloadBtn.setTextColor(getColor(mContext, mConfig.buttonTextColor));
        networkReloadBtn.setTextColor(getColor(mContext, mConfig.buttonTextColor));
        noLoginReloadBtn.setTextColor(getColor(mContext, mConfig.buttonTextColor));

        if (mConfig.buttonHeight != -1)
        {
            errorReloadBtn.setHeight(dp2px(mContext, mConfig.buttonHeight));
            networkReloadBtn.setHeight(dp2px(mContext, mConfig.buttonHeight));
            noLoginReloadBtn.setHeight(dp2px(mContext, mConfig.buttonHeight));
        }
        if (mConfig.buttonWidth != -1)
        {
            errorReloadBtn.setWidth(dp2px(mContext, mConfig.buttonWidth));
            networkReloadBtn.setWidth(dp2px(mContext, mConfig.buttonWidth));
            noLoginReloadBtn.setWidth(dp2px(mContext, mConfig.buttonWidth));
        }

        this.addView(networkPage);
        this.addView(emptyPage);
        this.addView(errorPage);
        this.addView(loadingPage);
        this.addView(noLoginPage);
    }

    public void setStatus(@Flavour int status)
    {

        this.state = status;

        switch (status)
        {
            case SUCCESS:
                contentView.setVisibility(View.VISIBLE);
                emptyPage.setVisibility(View.GONE);
                errorPage.setVisibility(View.GONE);
                networkPage.setVisibility(View.GONE);
                noLoginPage.setVisibility(View.GONE);
                if (defineLoadingPage != null)
                {
                    defineLoadingPage.setVisibility(View.GONE);
                } else
                {
                    loadingPage.setVisibility(View.GONE);
                }
                break;

            case LOADING:
                contentView.setVisibility(View.GONE);
                emptyPage.setVisibility(View.GONE);
                errorPage.setVisibility(View.GONE);
                networkPage.setVisibility(View.GONE);
                noLoginPage.setVisibility(View.GONE);
                if (defineLoadingPage != null)
                {
                    defineLoadingPage.setVisibility(View.VISIBLE);
                } else
                {
                    loadingPage.setVisibility(View.VISIBLE);
                }
                break;

            case EMPTY:
                contentView.setVisibility(View.GONE);
                emptyPage.setVisibility(View.VISIBLE);
                errorPage.setVisibility(View.GONE);
                networkPage.setVisibility(View.GONE);
                noLoginPage.setVisibility(View.GONE);
                if (defineLoadingPage != null)
                {
                    defineLoadingPage.setVisibility(View.GONE);
                } else
                {
                    loadingPage.setVisibility(View.GONE);
                }
                break;

            case ERROR:
                contentView.setVisibility(View.GONE);
                loadingPage.setVisibility(View.GONE);
                emptyPage.setVisibility(View.GONE);
                errorPage.setVisibility(View.VISIBLE);
                networkPage.setVisibility(View.GONE);
                noLoginPage.setVisibility(View.GONE);
                if (defineLoadingPage != null)
                {
                    defineLoadingPage.setVisibility(View.GONE);
                } else
                {
                    loadingPage.setVisibility(View.GONE);
                }
                break;

            case NO_NETWORK:
                contentView.setVisibility(View.GONE);
                loadingPage.setVisibility(View.GONE);
                emptyPage.setVisibility(View.GONE);
                errorPage.setVisibility(View.GONE);
                networkPage.setVisibility(View.VISIBLE);
                noLoginPage.setVisibility(View.GONE);
                if (defineLoadingPage != null)
                {
                    defineLoadingPage.setVisibility(View.GONE);
                } else
                {
                    loadingPage.setVisibility(View.GONE);
                }
                break;

            case NO_LOGIN:
                contentView.setVisibility(View.GONE);
                loadingPage.setVisibility(View.GONE);
                emptyPage.setVisibility(View.GONE);
                errorPage.setVisibility(View.GONE);
                networkPage.setVisibility(View.GONE);
                noLoginPage.setVisibility(View.VISIBLE);
                if (defineLoadingPage != null)
                {
                    defineLoadingPage.setVisibility(View.GONE);
                } else
                {
                    loadingPage.setVisibility(View.GONE);
                }
                break;

            default:
                break;
        }
    }

    /**
     * 返回当前状态{SUCCESS, EMPTY, ERROR, NO_NETWORK, LOADING, No_login}
     */
    public int getStatus()
    {
        return state;
    }

    /**
     * 设置Empty状态提示文本，仅对当前所在的地方有效
     */
    public LoadingLayout setEmptyText(String text)
    {
        emptyText.setText(text);
        return this;
    }

    /**
     * 设置Error状态提示文本，仅对当前所在的地方有效
     */
    public LoadingLayout setErrorText(String text)
    {
        errorText.setText(text);
        return this;
    }

    /**
     * 设置No_Network状态提示文本，仅对当前所在的地方有效
     */
    public LoadingLayout setNoNetworkText(String text)
    {
        networkText.setText(text);
        return this;
    }

    /**
     * 设置Empty状态显示图片，仅对当前所在的地方有效
     */
    public LoadingLayout setEmptyImage(@DrawableRes int id)
    {
        emptyImg.setImageResource(id);
        return this;
    }

    /**
     * 设置Error状态显示图片，仅对当前所在的地方有效
     */
    public LoadingLayout setErrorImage(@DrawableRes int id)
    {
        errorImg.setImageResource(id);
        return this;
    }

    /**
     * 设置No_Network状态显示图片，仅对当前所在的地方有效
     */
    public LoadingLayout setNoNetworkImage(@DrawableRes int id)
    {
        networkImg.setImageResource(id);
        return this;
    }

    /**
     * 设置Empty状态提示文本的字体大小，仅对当前所在的地方有效
     */
    public LoadingLayout setEmptyTextSize(int sp)
    {
        emptyText.setTextSize(sp);
        return this;
    }

    /**
     * 设置Error状态提示文本的字体大小，仅对当前所在的地方有效
     */
    public LoadingLayout setErrorTextSize(int sp)
    {
        errorText.setTextSize(sp);
        return this;
    }

    /**
     * 设置No_Network状态提示文本的字体大小，仅对当前所在的地方有效
     */
    public LoadingLayout setNoNetworkTextSize(int sp)
    {
        networkText.setTextSize(sp);
        return this;
    }

    /**
     * 设置Empty状态图片的显示与否，仅对当前所在的地方有效
     */
    public LoadingLayout setEmptyImageVisible(boolean bool)
    {
        if (bool)
        {
            emptyImg.setVisibility(View.VISIBLE);
        } else
        {
            emptyImg.setVisibility(View.GONE);
        }
        return this;
    }

    /**
     * 设置Error状态图片的显示与否，仅对当前所在的地方有效
     */
    public LoadingLayout setErrorImageVisible(boolean bool)
    {
        if (bool)
        {
            errorImg.setVisibility(View.VISIBLE);
        } else
        {
            errorImg.setVisibility(View.GONE);
        }
        return this;
    }

    /**
     * 设置No_Network状态图片的显示与否，仅对当前所在的地方有效
     */
    public LoadingLayout setNoNetworkImageVisible(boolean bool)
    {
        if (bool)
        {
            networkImg.setVisibility(View.VISIBLE);
        } else
        {
            networkImg.setVisibility(View.GONE);
        }
        return this;
    }

    /**
     * 设置ReloadButton的文本，仅对当前所在的地方有效
     */
    public LoadingLayout setReloadButtonText(@NonNull String text)
    {
        errorReloadBtn.setText(text);
        networkReloadBtn.setText(text);
        return this;
    }

    /**
     * 设置ReloadButton的文本字体大小，仅对当前所在的地方有效
     */
    public LoadingLayout setReloadButtonTextSize(int sp)
    {
        errorReloadBtn.setTextSize(sp);
        networkReloadBtn.setTextSize(sp);
        return this;
    }

    /**
     * 设置ReloadButton的文本颜色，仅对当前所在的地方有效
     */
    public LoadingLayout setReloadButtonTextColor(@ColorRes int id)
    {
        errorReloadBtn.setTextColor(getColor(mContext, id));
        networkReloadBtn.setTextSize(getColor(mContext, id));
        return this;
    }

    /**
     * 设置ReloadButton的背景，仅对当前所在的地方有效
     */
    public LoadingLayout setReloadButtonBackgroundResource(@DrawableRes int id)
    {
        errorReloadBtn.setBackgroundResource(id);
        networkReloadBtn.setBackgroundResource(id);
        return this;
    }

    /**
     * 设置ReloadButton的监听器
     */
    public LoadingLayout setOnReloadListener(OnReloadListener listener)
    {
        this.listener = listener;
        return this;
    }

    /**
     * 自定义加载页面，仅对当前所在的Activity有效
     */
    public LoadingLayout setLoadingPage(View view)
    {

        defineLoadingPage = view;
        this.removeView(loadingPage);
        defineLoadingPage.setVisibility(View.GONE);
        this.addView(view);
        return this;
    }

    /**
     * 自定义加载页面，仅对当前所在的地方有效
     */
    public LoadingLayout setLoadingPage(@LayoutRes int id)
    {

        this.removeView(loadingPage);
        View view = LayoutInflater.from(mContext).inflate(id, null);
        defineLoadingPage = view;
        defineLoadingPage.setVisibility(View.GONE);
        this.addView(view);
        return this;
    }

    /**
     * 设置各种状态下view的背景色，仅对当前所在的地方有效
     */
    public LoadingLayout setDefineBackgroundColor(@ColorRes int color)
    {

        if (defineLoadingPage == null)
        {
            loadingPage.setBackgroundColor(getColor(mContext, color));
        } else
        {
            defineLoadingPage.setBackgroundColor(getColor(mContext, color));
        }
        errorPage.setBackgroundColor(getColor(mContext, color));
        emptyPage.setBackgroundColor(getColor(mContext, color));
        networkPage.setBackgroundColor(getColor(mContext, color));
        return this;
    }

    /**
     * 获取当前自定义的loadingpage
     */
    public View getLoadingPage()
    {

        return defineLoadingPage;
    }

    /**
     * 获取全局使用的loadingpage
     */
    public View getGlobalLoadingPage()
    {

        return loadingPage;
    }

    @IntDef ({SUCCESS, EMPTY, ERROR, NO_NETWORK, LOADING, NO_LOGIN})
    public @interface Flavour {

    }

    public interface OnReloadListener {

        /**
         * 再次加载
         * * @param view view
         */
        void onReload(View v);

        /**
         * 网络错误
         * * @param view view
         */
        void onNetworkError(View view);

        /**
         * 数据为空
         *
         * @param view view
         */
        void onEmpty(View view);
    }

    /**
     * 获取全局配置的class
     */
    public static Config getConfig()
    {

        return mConfig;
    }

    /**
     * 全局配置的Class，对所有使用到的地方有效
     */
    private static class Config {

        String emptyStr = "暂无数据";
        String errorStr = "加载失败，请稍后重试···";
        String netWorkStr = "无网络连接，请检查网络···";
        String reloadBtnStr = "点击重试";
        String noLoginStr = "您还未登录，请先登录";
        String noLoginBtnStr = "点击登录";
        int emptyImgId = R.drawable.commont_loading_empty;
        int errorImgId = R.drawable.commont_loading_error;
        int networkImgId = R.drawable.commont_loading_no_network;
        int noLoginImgId = R.drawable.commont_laoding_define_error;
        int reloadBtnId = R.drawable.reload_btn_selector;
        int tipTextSize = 14;
        int buttonTextSize = 14;
        int tipTextColor = R.color.page_empty_color;
        int buttonTextColor = R.color.page_empty_color;
        int buttonWidth = -1;
        int buttonHeight = -1;
        int loadingLayoutId = R.layout.commont_loading_widget_loading_page;
        View loadingView = null;
        int backgroundColor = R.color.white;

        public Config setNoLoginBtnStr(@NonNull String noLoginBtnStr)
        {
            this.noLoginBtnStr = noLoginBtnStr;
            return mConfig;
        }

        public Config setErrorText(@NonNull String text)
        {
            errorStr = text;
            return mConfig;
        }

        public Config setEmptyText(@NonNull String text)
        {
            emptyStr = text;
            return mConfig;
        }

        public Config setNoNetworkText(@NonNull String text)
        {
            netWorkStr = text;
            return mConfig;
        }

        public Config setReloadButtonText(@NonNull String text)
        {
            reloadBtnStr = text;
            return mConfig;
        }

        /**
         * 设置所有提示文本的字体大小
         */
        public Config setAllTipTextSize(int sp)
        {
            tipTextSize = sp;
            return mConfig;
        }

        /**
         * 设置所有提示文本的字体颜色
         */
        public Config setAllTipTextColor(@ColorRes int color)
        {
            tipTextColor = color;
            return mConfig;
        }

        public Config setReloadButtonTextSize(int sp)
        {
            buttonTextSize = sp;
            return mConfig;
        }

        public Config setReloadButtonTextColor(@ColorRes int color)
        {
            buttonTextColor = color;
            return mConfig;
        }

        public Config setReloadButtonBackgroundResource(@DrawableRes int id)
        {
            reloadBtnId = id;
            return mConfig;
        }

        public Config setReloadButtonWidthAndHeight(int width_dp, int height_dp)
        {
            buttonWidth = width_dp;
            buttonHeight = height_dp;
            return mConfig;
        }

        public Config setErrorImage(@DrawableRes int id)
        {
            errorImgId = id;
            return mConfig;
        }

        public Config setEmptyImage(@DrawableRes int id)
        {
            emptyImgId = id;
            return this;
        }

        public Config setNoLoginImage(@DrawableRes int id)
        {
            noLoginImgId = id;
            return this;
        }


        public Config setNoNetworkImage(@DrawableRes int id)
        {
            networkImgId = id;
            return mConfig;
        }

        public Config setLoadingPageLayout(@LayoutRes int id)
        {
            loadingLayoutId = id;
            return mConfig;
        }

        public Config setLoadingPageView(View view)
        {
            this.loadingView = view;
            return mConfig;
        }

        public Config setAllPageBackgroundColor(@ColorRes int color)
        {
            backgroundColor = color;
            return mConfig;
        }
    }

    /**
     * 获取View
     */
    public static <T extends View> T findViewById(View v, int id)
    {
        return (T) v.findViewById(id);
    }

    /**
     * dip转换为px
     */
    public static int dp2px(Context context, int dip)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /**
     * 获取定义的资源id
     */
    public static int getColor(Context conetxt, @ColorRes int id)
    {
        return ContextCompat.getColor(conetxt, id);
    }

}
