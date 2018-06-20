package com.seabig.moduledemo.widget.ui.activity;

import android.content.Intent;
import android.view.View;

import com.seabig.moduledemo.common.base.BaseActivity;
import com.seabig.moduledemo.widget.R;

/**
 * @author YJZ
 *         date 2018/6/3
 *         description 自定义控件首页
 */

public class WidgetHomeActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected int onSettingUpContentViewResourceID() {
        return R.layout.widget_activity_home;
    }

    @Override
    protected void onSettingUpView() {
        findViewById(R.id.one_btn).setOnClickListener(this);
        findViewById(R.id.two_btn).setOnClickListener(this);
        findViewById(R.id.three_btn).setOnClickListener(this);
        findViewById(R.id.four_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.one_btn) {
            startActivity(ScreenSlideActivity.class);
        } else if (i == R.id.two_btn) {
            startActivity(EchelonActivity.class);
        } else if (i == R.id.three_btn) {
            startActivity(ARouterActivity.class);
        } else if (i == R.id.four_btn) {
            startActivity(MaterDesignActivity.class);
        }
    }

    private void startActivity(Class activityCls) {
        startActivity(new Intent(this, activityCls));
    }
}
