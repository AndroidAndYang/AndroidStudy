package com.seabig.moduledemo.widget.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.seabig.moduledemo.common.base.BaseRecyclerAdapter;
import com.seabig.moduledemo.widget.R;
import com.seabig.moduledemo.widget.bean.EchelonBean;

import java.util.List;

/**
 * @author YJZ
 *         date 2018/6/3
 */

public class EchelonAdapter extends BaseRecyclerAdapter<EchelonBean> {

    private Context mContext;

    public EchelonAdapter(@NonNull Context context, List<EchelonBean> data)
    {
        super(context, R.layout.widget_adapter_echelon, data);
        this.mContext = context;
    }

    @Override
    public void convert(ViewHolder holder, EchelonBean echelonBean, int position)
    {
        holder.setImageResource(R.id.img_icon, ContextCompat.getDrawable(mContext, echelonBean.getHead()));
        holder.setImageResource(R.id.img_bg, ContextCompat.getDrawable(mContext, echelonBean.getBg()));
        holder.setText(R.id.tv_nickname, echelonBean.getName());
        holder.setText(R.id.tv_desc, echelonBean.getDes());
    }
}
