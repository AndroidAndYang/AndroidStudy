package com.seabig.moduledemo.widget.ui.activity;

import com.seabig.moduledemo.common.base.BaseActivity;
import com.seabig.moduledemo.widget.R;
import com.seabig.moduledemo.widget.ui.widget.SeatTable;

import android.support.v7.widget.Toolbar;

/**
 * author： YJZ
 * date:  2018/7/18
 * des: 电影选座
 */

public class SeatTableActivity extends BaseActivity {

    @Override
    protected int onSettingUpContentViewResourceID() {
        return R.layout.widget_activity_seat_table;
    }

    @Override
    protected void onSettingUpView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolBar(toolbar, getStringByResId(R.string.fiex_head_two));

        SeatTable seatTableView = (SeatTable) findViewById(R.id.seat_table);
        seatTableView.setScreenName("8号厅荧幕");//设置屏幕名称
        seatTableView.setMaxSelected(3);//设置最多选中
        seatTableView.setSeatChecker(new SeatTable.SeatChecker() {
            @Override
            public boolean isValidSeat(int row, int column) {
                if (column == 2) {
                    return false;
                }
                return true;
            }

            @Override
            public boolean isSold(int row, int column) {
                if (row == 6 && column == 6) {
                    return true;
                }
                return false;
            }

            @Override
            public void checked(int row, int column) {

            }

            @Override
            public void unCheck(int row, int column) {

            }

            @Override
            public String[] checkedSeatTxt(int row, int column) {
                return null;
            }

        });
        seatTableView.setData(10, 15);
    }
}
