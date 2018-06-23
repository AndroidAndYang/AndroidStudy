package com.android.library.util;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import com.android.library.R;


/**
 * Author: YJZ
 * Time: 2017/9/29
 * Desc:
 */

public class DialogUtil {

    public static void showDialog(Context context, String hint, String rightTv, String leftTv, DialogInterface.OnClickListener listener)
    {
        AlertDialog dialog = new AlertDialog.Builder(context).setMessage(hint).setPositiveButton(rightTv, listener).setNegativeButton(leftTv, null).create();
        dialog.show();
        // 显示Dialog左边按钮的文字颜色
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(context, R.color.main_text_color));
        // 显示Dialog右边按钮的文字颜色
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(context, R.color.main_color));
    }

    public static void showDialog(Context context, String hint,DialogInterface.OnClickListener listener, DialogInterface.OnClickListener cancelListener)
    {
        AlertDialog dialog = new AlertDialog.Builder(context).setMessage(hint).setPositiveButton("确定", listener).setNegativeButton("取消", cancelListener).create();
        dialog.show();
        // 显示Dialog左边按钮的文字颜色
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(context, R.color.main_text_color));
        // 显示Dialog右边按钮的文字颜色
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(context, R.color.main_color));
    }

    public static void showDialog(Context context, String hint, String rightTv, String leftTv, DialogInterface.OnClickListener listener, DialogInterface.OnClickListener cancelListener)
    {
        AlertDialog dialog = new AlertDialog.Builder(context).setMessage(hint).setPositiveButton(rightTv, listener).setNegativeButton(leftTv, cancelListener).create();
        dialog.show();
        // 显示Dialog左边按钮的文字颜色
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(context, R.color.main_text_color));
        // 显示Dialog右边按钮的文字颜色
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(context, R.color.main_color));
    }

    public static void showDialog(final Context context, String hint)
    {
        showDialog(context, hint, "确定", "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                intent.setData(uri);
                context.startActivity(intent);
            }
        });
    }
}
