package com.seabig.moduledemo.widget.adapter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.seabig.moduledemo.common.base.BaseRecyclerAdapter;
import com.seabig.moduledemo.widget.R;

import java.util.List;

/**
 * author： YJZ
 * date:  2018/6/4
 * des:
 */

public class MaterDesginAdapter extends BaseRecyclerAdapter<String> {

    public MaterDesginAdapter(@NonNull Context context, List<String> data) {
        super(context, R.layout.widget_adapter_materdesign, data);
    }

    @Override
    public void convert(ViewHolder holder, String content, int position) {
        holder.setText(R.id.content,content);
    }
}
