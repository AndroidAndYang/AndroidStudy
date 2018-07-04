package com.seabig.moduledemo.common.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.android.library.util.permission.Permission;
import com.android.library.util.permission.PermissionUtil;
import com.android.library.util.permission.callback.Action;
import com.seabig.moduledemo.common.R;

import java.io.File;
import java.util.List;

/**
 * @author YJZ
 *         date: 2017/12/11
 *         description：系统界面工具类
 */

public class SystemUtils {

    /**
     * 系统文件路径
     */
    private static final String[] SYSTEM_PATH_ARR = {"/system/bin/", "/system/xbin/", "/system/sbin/", "/sbin/", "/vendor/bin/"};

    /**
     * 发送短信, 如果需要可以做非空判断
     *
     * @param number 发送的号码
     * @param body   内容
     */
    public static void sendSMS(final Context context, final String number, final String body) {

        PermissionUtil.with(context).permission(Permission.SEND_SMS).onDenied(new Action() {
            @Override
            public void onAction(List<String> permissions) {
                ToastUtils.getInstance().showToast(context, "没有短信权限");
            }
        }).onGranted(new Action() {
            @Override
            public void onAction(List<String> permissions) {
                Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
                sendIntent.setData(Uri.parse("smsto:" + number));
                sendIntent.putExtra("sms_body", body);
                context.startActivity(sendIntent);
            }
        }).start();
    }

    /**
     * 跳转到拨号界面,如果需要可以做非空判断
     *
     * @param phoneNumber 電話
     */
    public static void forwardToDial(final Context context, final String phoneNumber) {
        PermissionUtil.with(context).permission(Permission.CALL_PHONE).onDenied(new Action() {
            @Override
            public void onAction(List<String> permissions) {
                ToastUtils.getInstance().showToast(context, "没有电话权限");
            }
        }).onGranted(new Action() {
            @Override
            public void onAction(List<String> permissions) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneNumber));
                context.startActivity(intent);
            }
        }).start();
    }

    /**
     * 发邮件
     */
    public static void openEmail(@NonNull Context context, @NonNull String email, @NonNull String subject, @NonNull String text) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("mailto:" + email));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, text);
            context.startActivity(intent);
        } else {
            ToastUtils.getInstance().showToast(context,"系统中没有安装邮件客户端");
        }
    }

    /**
     * 打开浏览器
     */
    public static void openWeb(Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            ToastUtils.getInstance().showToast(context, "系统中没有安装浏览器");
        }
    }

    /**
     * 打开联系人
     *
     * @param requestCode 请求码
     */
    public static void openContacts(final Activity context, final int requestCode) {

        PermissionUtil.with(context).permission(Permission.READ_CONTACTS).onDenied(new Action() {
            @Override
            public void onAction(List<String> permissions) {
                ToastUtils.getInstance().showToast(context, "没有读取联系人权限");
            }
        }).onGranted(new Action() {
            @Override
            public void onAction(List<String> permissions) {
                Uri uri = Uri.parse("content://contacts/people");
                context.startActivityForResult(new Intent(Intent.ACTION_PICK, uri), requestCode);
            }
        }).start();
    }

    /**
     * 打开系统设置界面
     */
    public static void openSettings(Activity context, String action) {
        Intent intent = new Intent();
        ComponentName comp = new ComponentName("com.android.settings", action);
        intent.setComponent(comp);
        intent.setAction("android.intent.action.VIEW");
        context.startActivityForResult(intent, 0);
    }

    /**
     * 打开系统图库
     *
     * @param requestCode 请求码
     */
    public static void openAlbum(Context context, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        ((Activity) context).startActivityForResult(intent, requestCode);
    }

    /**
     * 是否已ROOT
     */
    public static boolean isRooted() {
        File file;
        boolean flag1 = false;
        for (String suSearchPath : SYSTEM_PATH_ARR) {
            file = new File(suSearchPath + "su");
            if (file.isFile() && file.exists()) {
                flag1 = true;
                break;
            }
        }
        return flag1;
    }

    /**
     * 返回手机主界面
     */
    public static void goHome(Context context) {
        Intent mHomeIntent = new Intent(Intent.ACTION_MAIN);
        mHomeIntent.addCategory(Intent.CATEGORY_HOME);
        mHomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        context.startActivity(mHomeIntent);
    }
}
