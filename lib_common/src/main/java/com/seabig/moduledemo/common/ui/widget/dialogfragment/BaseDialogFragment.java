package com.seabig.moduledemo.common.ui.widget.dialogfragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.seabig.moduledemo.common.R;
import com.seabig.moduledemo.common.util.ScreenUtils;

/**
 * @author YJZ
 *         date 2018/3/16
 *         description DialogFragment基类
 */

public class BaseDialogFragment extends DialogFragment {

    /**
     * layout id
     */
    @LayoutRes
    public int mLayoutID;

    /**
     * 是否显示在底部
     */
    public boolean mIsShowBottom = false;

    /**
     * 左右边距
     */
    private int margin;

    /**
     * 宽度
     */
    private int width;

    /**
     * 高度
     */
    private int height;

    /**
     * 是否点击外部取消
     */
    private boolean mOutCancel = true;

    /**
     * 视图监听
     */
    private ViewConvertListener convertListener;

    private static BaseDialogFragment mBaseDialogFragment;

    public static BaseDialogFragment getInstance()
    {
        if (mBaseDialogFragment == null)
        {
            synchronized (BaseDialogFragment.class)
            {
                if (mBaseDialogFragment == null)
                {
                    return mBaseDialogFragment = new BaseDialogFragment();
                }
            }
        }
        return mBaseDialogFragment;
    }

    public BaseDialogFragment setDialogLayout(int layoutID)
    {
        this.mLayoutID = layoutID;
        return this;
    }

    /**
     * 是否在底部显示
     *
     * @param isShowBottom boolean
     */
    public BaseDialogFragment isShowBottom(boolean isShowBottom)
    {
        this.mIsShowBottom = isShowBottom;
        return this;
    }

    public BaseDialogFragment setMargin(int margin)
    {
        this.margin = margin;
        return this;
    }

    public BaseDialogFragment setWidth(int width)
    {
        this.width = width;
        return this;
    }

    public BaseDialogFragment setHeight(int height)
    {
        this.height = height;
        return this;
    }

    public BaseDialogFragment isOutCancel(boolean outCancel)
    {
        this.mOutCancel = outCancel;
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // 去除DialogFragment的标题
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogStyle);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        initParams();
    }

    private void initParams()
    {
        Window window = getDialog().getWindow();
        if (window != null)
        {
            // 设置Dialog进入退出动画
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            // 调节灰色透明背景
            layoutParams.dimAmount = 0.5f;
            // 是否现在显示底部
            if (mIsShowBottom)
            {
                layoutParams.gravity = Gravity.BOTTOM;
            }

            //设置dialog宽度
            if (width == 0)
            {
                layoutParams.width = ScreenUtils.getScreenWidth(getContext()) - 2 * ScreenUtils.dipToPx(getContext(), margin);
            } else
            {
                layoutParams.width = ScreenUtils.dipToPx(getContext(), width);
            }

            //设置dialog高度
            if (height == 0)
            {
                layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            } else
            {
                layoutParams.height = ScreenUtils.dipToPx(getContext(), height);
            }

            window.setAttributes(layoutParams);
        }
        // 点击外部消失
        setCancelable(mOutCancel);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(mLayoutID, null);
        convertListener.convertView(ViewHolder.create(view), this);
        return view;
    }

    public BaseDialogFragment setConvertListener(ViewConvertListener convertListener)
    {
        this.convertListener = convertListener;
        return this;
    }

    public BaseDialogFragment show(FragmentManager manager)
    {
        super.show(manager, String.valueOf(System.currentTimeMillis()));
        return this;
    }
}
