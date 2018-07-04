package com.seabig.moduledemo.common.base;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * author: YJZ
 * date: 2017/12/11
 * description：万能RecycleView的Adapter
 */

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerAdapter.ViewHolder> {

    protected Context mContext;
    private List<T> mData;
    private int mLayoutResId;
    private OnRecyclerViewItemClickListen mOnRecyclerViewItemClickListen;
    private boolean isAddParent;

    public BaseRecyclerAdapter(@NonNull Context context, int layoutResId, List<T> data)
    {
        this.mData = data == null ? new ArrayList<T>() : new ArrayList<>(data);
        this.mContext = context;
        this.mLayoutResId = layoutResId;
    }

    public BaseRecyclerAdapter(@NonNull Context context, int layoutResId, List<T> data, boolean isAddParent)
    {
        this.mData = data == null ? new ArrayList<T>() : new ArrayList<>(data);
        this.mContext = context;
        this.mLayoutResId = layoutResId;
        this.isAddParent = isAddParent;
    }

    public interface OnRecyclerViewItemClickListen {
        /**
         * 定义RecycleView的item点击事件
         *
         * @param view     item
         * @param position 索引
         */
        void onItemClickListen(View view, int position);
    }

    public T getItem(int position)
    {
        return position < 0 || position >= mData.size() ? null : mData.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        final View view = LayoutInflater.from(parent.getContext()).inflate(mLayoutResId, isAddParent ? parent : null, false);
        final ViewHolder holder = new ViewHolder(mContext, view);
        //给控件设置点击事件
        if (mOnRecyclerViewItemClickListen != null)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    mOnRecyclerViewItemClickListen.onItemClickListen(view, holder.getLayoutPosition());
                }
            });
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        convert(holder, mData.get(position), position);
    }

    /**
     * 转化View
     *
     * @param holder   ViewHolder
     * @param t        实体类
     * @param position 索引
     */
    public abstract void convert(ViewHolder holder, T t, int position);

    @Override
    public int getItemCount()
    {
        return mData != null ? mData.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * 将holder中的子view存储到SparseArray中
         */
        private final SparseArray<View> mViews;
        private final Context mContext;
        private View mConvertView;

        private ViewHolder(Context context, View itemView)
        {
            super(itemView);
            this.mContext = context;
            this.mConvertView = itemView;
            this.mViews = new SparseArray<>();
        }

        /**
         * 每次操作子view前,执行此方法,若该子view已在SparseArray中z则直接返回,如不存在则实例化它并将其存放至SparseArray
         */
        private <T extends View> T retrieveView(int viewId)
        {
            View view = mViews.get(viewId);
            if (view == null)
            {
                view = mConvertView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return (T) view;
        }

        public View getView(int viewId)
        {
            return retrieveView(viewId);
        }

        /**
         * 处理子View (根据具体情况还可增加setChecked,setTag,SetVisible等方法)
         */
        public ViewHolder setText(int viewId, String value)
        {
            TextView view = retrieveView(viewId);
            view.setText(value);
            return this;
        }

        public ViewHolder setCharSequence(int viewId, CharSequence value)
        {
            TextView view = retrieveView(viewId);
            view.setText(value);
            return this;
        }

        public ViewHolder setImageResource(int viewId, int imageResId)
        {
            ImageView view = retrieveView(viewId);
            view.setImageResource(imageResId);
            return this;
        }

        public ViewHolder setImageResource(int viewId, Drawable drawable)
        {
            ImageView view = retrieveView(viewId);
            view.setImageDrawable(drawable);
            return this;
        }

        public ViewHolder setImageURI(int viewId, String imageURI)
        {
            ImageView view = retrieveView(viewId);
            Glide.with(mContext).load(imageURI).into(view);
            return this;
        }

        public ViewHolder setBackgroundColor(int viewId, int color)
        {
            View view = retrieveView(viewId);
            view.setBackgroundColor(color);
            return this;
        }


        public ViewHolder setBackground(int viewId, Drawable drawable)
        {
            View view = retrieveView(viewId);
            view.setBackground(drawable);
            return this;
        }

        public ViewHolder setBackgroundColor(int viewId, Drawable drawable)
        {
            View view = retrieveView(viewId);
            view.setBackground(drawable);
            return this;
        }

        public ViewHolder setVisible(int viewId, boolean isVisible)
        {
            View view = retrieveView(viewId);
            view.setVisibility(isVisible ? View.GONE : View.VISIBLE);
            return this;
        }

        public ViewHolder setVisible(int viewId, int isVisible)
        {
            View view = retrieveView(viewId);
            view.setVisibility(isVisible);
            return this;
        }

        public ViewHolder setOnClickListener(int viewId, View.OnClickListener onClickListener)
        {
            View view = retrieveView(viewId);
            view.setOnClickListener(onClickListener);
            return this;
        }

        public ViewHolder setClickable(int viewId, boolean clickable)
        {
            View view = retrieveView(viewId);
            view.setClickable(clickable);
            return this;
        }
    }

    public void remove(int index)
    {
        if (index >= 0 && index < getItemCount())
        {
            mData.remove(index);
            notifyItemRemoved(index);
        }
    }

    public void setOnRecyclerViewItemClickListen(OnRecyclerViewItemClickListen mOnRecyclerViewItemClickListen)
    {
        this.mOnRecyclerViewItemClickListen = mOnRecyclerViewItemClickListen;
    }
}
