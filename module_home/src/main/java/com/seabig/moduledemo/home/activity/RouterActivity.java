package com.seabig.moduledemo.home.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

/**
 * @author YJZ
 *         date 2018/5/3
 *         description 路由Activity
 */

// 在支持路由的页面上添加注解(必选)
// 这里的路径需要注意的是至少需要有两级，/xx/xx
@Route (path = "/one/router")
public class RouterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        TextView textView = new TextView(this);
        textView.setText("RouterActivity");
        setContentView(textView);
    }
}
