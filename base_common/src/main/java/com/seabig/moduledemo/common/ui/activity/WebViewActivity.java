package com.seabig.moduledemo.common.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.seabig.moduledemo.common.R;
import com.seabig.moduledemo.common.base.BaseActivity;
import com.seabig.moduledemo.common.datamgr.AppConstant;
import com.seabig.moduledemo.common.ui.widget.LoadingLayout;


/**
 * @author YJZ
 *         time 2016/8/11  15:33
 *         desc Web界面
 */

@Route(path = "/common/activity/webView")
public class WebViewActivity extends BaseActivity {

    private WebView mWebView;
    private LoadingLayout mLoadingLayout;
    private String mLinkUrl;

    @Override
    protected int onSettingUpContentViewResourceID() {
        return R.layout.common_activity_util_web_view;
    }

    @Override
    protected void onSettingUpView() {
        initViewAndData();
        initWebView();
    }

    private void initViewAndData() {

        Bundle bundle = getIntent().getBundleExtra("bundle");
        mLinkUrl = bundle.getString(AppConstant.WEBVIEW_URL);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(bundle.getString(AppConstant.WEBVIEW_TITLE));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mWebView = (WebView) findViewById(R.id.web_view);
        mLoadingLayout = (LoadingLayout) findViewById(R.id.loading_layout);
    }

    private void initWebView() {
        mWebView.loadUrl(mLinkUrl);
        //允许WebView加载JavaScript
        mWebView.getSettings().setJavaScriptEnabled(true);
        // 使WebView响应点击时间
        mWebView.setWebChromeClient(new WebChromeClient() {

        });
        //html 显示内容大于屏幕的宽度的时候,
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        //处理javaScript交互
        mWebView.setWebViewClient(new WebViewClient() {
            //当WebView加载的网页发生跳转时被调用的
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            //WebView开始加载url时的回调
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                mLoadingLayout.setStatus(LoadingLayout.LOADING);
            }

            //WebView开始加载url完成时的回调
            @Override
            public void onPageFinished(WebView view, String url) {
                mLoadingLayout.setStatus(LoadingLayout.SUCCESS);
            }

            //WebView接收错误
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                mLoadingLayout.setStatus(LoadingLayout.ERROR);
            }
        });
    }

    /**
     * 解决WebView在退出时没有释放音频
     */
    @Override
    protected void onPause() {
        mWebView.reload();
        super.onPause();
    }
}
