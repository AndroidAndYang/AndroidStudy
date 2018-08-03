package com.seabig.moduledemo.widget.ui.activity;

import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.android.library.util.ToastUtils;
import com.seabig.moduledemo.common.base.BaseActivity;
import com.seabig.moduledemo.common.util.ActivityUtils;
import com.seabig.moduledemo.widget.R;
import com.seabig.moduledemo.widget.adapter.MaterDesginAdapter;

import java.util.ArrayList;

/**
 * author： YJZ
 * date:  2018/6/4
 * des: MD控件使用
 */
@Route(path = "/widget/activity/mater_design")
public class MaterDesignActivity extends BaseActivity implements View.OnClickListener {

    private View toolbar1;
    private View toolbar2;
    private AppBarLayout mAppBarLayout;
    private RecyclerView mRv;
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
                // int alphaIn = offset;
                // int alphaOut = (200 - offset) < 0 ? 0 : 200 - offset;

                // int maskColorIn = Color.argb(alphaIn, Color.red(mMaskColor),
                //         Color.green(mMaskColor), Color.blue(mMaskColor));

                // int maskColorInDouble = Color.argb(alphaIn, Color.red(mMaskColor),
                //         Color.green(mMaskColor), Color.blue(mMaskColor));

                // int maskColorOut = Color.argb(alphaOut, Color.red(mMaskColor),
                //         Color.green(mMaskColor), Color.blue(mMaskColor));

                if (offset <= total / 2) {
                    toolbar1.setVisibility(View.VISIBLE);
                    toolbar2.setVisibility(View.GONE);
                    // toolbar1.setBackgroundColor(maskColorInDouble);
                } else {
                    toolbar1.setVisibility(View.GONE);
                    toolbar2.setVisibility(View.VISIBLE);
                    // toolbar2.setBackgroundColor(maskColorOut);
                }

                // v_pay_mask.setBackgroundColor(maskColorIn);
            }
        });
    }

    private void initView() {
        toolbar1 = findViewById(R.id.toolbar1);
        toolbar2 = findViewById(R.id.toolbar2);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        mRv = (RecyclerView) findViewById(R.id.rv);

        findViewById(R.id.toolbar1_right).setOnClickListener(this);
        findViewById(R.id.close_one).setOnClickListener(this);
        findViewById(R.id.close_two).setOnClickListener(this);
        findViewById(R.id.close_three).setOnClickListener(this);
        findViewById(R.id.close_four).setOnClickListener(this);

        findViewById(R.id.open_one).setOnClickListener(this);
        findViewById(R.id.open_two).setOnClickListener(this);
        findViewById(R.id.open_three).setOnClickListener(this);
        findViewById(R.id.open_four).setOnClickListener(this);

        v_pay_mask = findViewById(R.id.v_pay_mask);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.close_one) {
            ToastUtils.getInstance().showToast(this, "close_one");
        } else if (i == R.id.close_two) {
            ToastUtils.getInstance().showToast(this, "close_two");
        } else if (i == R.id.close_three) {
            ToastUtils.getInstance().showToast(this, "close_three");
        } else if (i == R.id.close_four) {
            ToastUtils.getInstance().showToast(this, "close_four");
        } else if (i == R.id.open_one) {
            ToastUtils.getInstance().showToast(this, "open_one");
        } else if (i == R.id.open_two) {
            ActivityUtils.INSTANCE.startActivity(this,SeatTableActivity.class);
        } else if (i == R.id.open_three) {
            ToastUtils.getInstance().showToast(this, "open_three");
        } else if (i == R.id.open_four) {
            ToastUtils.getInstance().showToast(this, "open_four");
        } else if (i == R.id.toolbar1_right) {
            ToastUtils.getInstance().showToast(this, "toolbar_right");

            ActivityUtils.INSTANCE.startActivity(this, LifecyclePresenterActivity.class);

        }
    }
}
