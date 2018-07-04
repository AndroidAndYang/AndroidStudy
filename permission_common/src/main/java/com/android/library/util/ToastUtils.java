package com.android.library.util;

import android.content.Context;
import android.widget.Toast;

/**
 * @author YJZ
 * date: 2017/12/11
 * description：ToastUtil
 */

public class ToastUtils {

    private static ToastUtils mInstance;
    private Toast mToast;

    public static ToastUtils getInstance()
    {
        if (mInstance == null)
        {
            synchronized (ToastUtils.class)
            {
                if (mInstance == null)
                {
                    return mInstance = new ToastUtils();
                }
            }
        }
        return mInstance;
    }

    public void showToast(Context context, String text)
    {
        cancelToast();
        mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        mToast.show();
    }

    private void cancelToast()
    {
        if (mToast != null)
        {
            mToast.cancel();
            mToast = null;
        }
    }
}
