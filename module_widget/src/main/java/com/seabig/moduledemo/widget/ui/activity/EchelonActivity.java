package com.seabig.moduledemo.widget.ui.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.seabig.moduledemo.common.base.BaseActivity;
import com.seabig.moduledemo.common.base.BaseRecyclerAdapter;
import com.seabig.moduledemo.common.widget.layoutmanager.EchelonLayoutManager;
import com.seabig.moduledemo.widget.R;
import com.seabig.moduledemo.widget.adapter.EchelonAdapter;
import com.seabig.moduledemo.widget.bean.EchelonBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author YJZ
 *         date 2018/6/3
 *         description
 */

public class EchelonActivity extends BaseActivity {

    @Override
    protected int onSettingUpContentViewResourceID()
    {
        return R.layout.widget_activity_echelon;
    }

    @Override
    protected void onSettingUpView()
    {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.two_recycler_view);
        recyclerView.setLayoutManager(new EchelonLayoutManager());
        List<EchelonBean> echelonBeanList = new ArrayList<>();
        int[] icons = {R.drawable.widget_echelon_header_icon_one, R.drawable.widget_echelon_header_icon_two,
                R.drawable.widget_echelon_header_icon_three, R.drawable.widget_echelon_header_icon_four};
        int[] bgs = {R.drawable.widget_echelon__bg_one, R.drawable.widget_echelon__bg_two,
                R.drawable.widget_echelon__bg_three, R.drawable.widget_echelon__bg_four};
        String[] nickNames = {"左耳近心", "凉雨初夏", "稚久九栀", "半窗疏影"};
        String[] descs = {
                "回不去的地方叫故乡 没有根的迁徙叫流浪...",
                "人生就像迷宫，我们用上半生找寻入口，用下半生找寻出口",
                "原来地久天长，只是误会一场",
                "不是故事的结局不够好，而是我们对故事的要求过多",
                "只想优雅转身，不料华丽撞墙"
        };

        for (int i = 0; i < 8; i++)
        {
            EchelonBean echelonBean = new EchelonBean();
            echelonBean.setBg(bgs[i % 4]);
            echelonBean.setDes(descs[i % 5]);
            echelonBean.setHead(icons[i % 4]);
            echelonBean.setName(nickNames[i % 4]);
            echelonBeanList.add(echelonBean);
        }

        EchelonAdapter echelonAdapter = new EchelonAdapter(this, echelonBeanList);
        echelonAdapter.setOnRecyclerViewItemClickListen(new BaseRecyclerAdapter.OnRecyclerViewItemClickListen() {
            @Override
            public void onItemClickListen(View view, int position)
            {
                showToast("position = " + position);
            }
        });
        recyclerView.setAdapter(echelonAdapter);
    }
}
