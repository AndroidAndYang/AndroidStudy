package com.seabig.moduledemo.widget.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.seabig.moduledemo.common.base.BaseActivity;
import com.seabig.moduledemo.widget.R;

/**
 * @author YJZ
 * @date 2018/6/3
 * @description
 */

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected int onSettingUpContentViewResourceID()
    {
        return R.layout.widget_activity_main;
    }

    @Override
    protected void onSettingUpView()
    {
        findViewById(R.id.one_btn).setOnClickListener(this);
        findViewById(R.id.two_btn).setOnClickListener(this);
        findViewById(R.id.three_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.one_btn:
                startActivity(ScreenSlideActivity.class);
                break;

            case R.id.two_btn:
                startActivity(EchelonActivity.class);
                break;
            case R.id.three_btn:
                startActivity(ARouterActivity.class);
                break;

            default:
                break;
        }
    }

    private void startActivity(Class activityCls)
    {
        startActivity(new Intent(this, activityCls));
    }
}
