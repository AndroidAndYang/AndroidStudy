package com.seabig.moduledemo.widget.ui.activity;

import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.seabig.moduledemo.common.base.BaseActivity;
import com.seabig.moduledemo.widget.R;
import com.seabig.moduledemo.widget.adapter.MaterDesginAdapter;

import java.util.ArrayList;

/**
 * author： YJZ
 * date:  2018/6/4
 * des: MD控件使用
 */

public class MaterDesignActivity extends BaseActivity {

    private ImageView mImgZhangdan;
    private TextView mImgZhangdanTxt;
    private View toolbar1;
    private View toolbar2;
    private ImageView mJiahao;
    private ImageView mTongxunlu;
    private ImageView mImgShaomiao;
    private ImageView mImgFukuang;
    private ImageView mImgSearch;
    private ImageView mImgZhaoxiang;
    private AppBarLayout mAppBarLayout;
    private RecyclerView mRv;
    private CoordinatorLayout mActivityMain;
    private View v_pay_mask;

    @Override
    protected int onSettingUpContentViewResourceID() {
        return R.layout.widget_activity_md;
    }

    @Override
    protected void onSettingUpView() {
        initView();
    }

    private int mMaskColor;

    @Override
    protected void onSettingUpData() {

        mRv.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            arrayList.add("数字：" + i);
        }
        mRv.setAdapter(new MaterDesginAdapter(this, arrayList));

        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int offset = Math.abs(verticalOffset);
                int total = appBarLayout.getTotalScrollRange();
                int alphaIn = offset;
                int alphaOut = (200 - offset) < 0 ? 0 : 200 - offset;

                int maskColorIn = Color.argb(alphaIn, Color.red(mMaskColor),
                        Color.green(mMaskColor), Color.blue(mMaskColor));

                int maskColorInDouble = Color.argb(alphaIn, Color.red(mMaskColor),
                        Color.green(mMaskColor), Color.blue(mMaskColor));

                int maskColorOut = Color.argb(alphaOut, Color.red(mMaskColor),
                        Color.green(mMaskColor), Color.blue(mMaskColor));

                if (offset <= total / 2) {
                    toolbar1.setVisibility(View.VISIBLE);
                    toolbar2.setVisibility(View.GONE);
                    toolbar1.setBackgroundColor(maskColorInDouble);
                } else {
                    toolbar1.setVisibility(View.GONE);
                    toolbar2.setVisibility(View.VISIBLE);
                    toolbar2.setBackgroundColor(maskColorOut);
                }

                v_pay_mask.setBackgroundColor(maskColorIn);
            }
        });
    }

    private void initView() {
        mActivityMain = (CoordinatorLayout) findViewById(R.id.activity_main);
        mImgZhangdan = (ImageView) findViewById(R.id.img_zhangdan);
        mImgZhangdanTxt = (TextView) findViewById(R.id.img_zhangdan_txt);
        toolbar1 = findViewById(R.id.toolbar1);
        toolbar2 = findViewById(R.id.toolbar2);
        mJiahao = (ImageView) findViewById(R.id.jiahao);
        mTongxunlu = (ImageView) findViewById(R.id.tongxunlu);
        mImgShaomiao = (ImageView) findViewById(R.id.img_shaomiao);
        mImgFukuang = (ImageView) findViewById(R.id.img_fukuang);
        mImgSearch = (ImageView) findViewById(R.id.img_search);
        mImgZhaoxiang = (ImageView) findViewById(R.id.img_zhaoxiang);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        mRv = (RecyclerView) findViewById(R.id.rv);
        mActivityMain = (CoordinatorLayout) findViewById(R.id.activity_main);
        v_pay_mask = findViewById(R.id.v_pay_mask);
    }
}
