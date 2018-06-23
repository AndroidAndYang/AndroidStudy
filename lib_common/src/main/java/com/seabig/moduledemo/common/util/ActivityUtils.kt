package com.seabig.moduledemo.common.util

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils

/**
 * @author YJZ
 * *         date: 2017/12/15
 * *         description：Activity工具类
 */

object ActivityUtils {

    /**
     * 启动一个不带参数的Activity
     * @param cla Activity
     */
    fun startActivity(context: Context, cla: Class<*>) {
        val intent = Intent(context, cla)
        context.startActivity(intent)
    }


    /**
     * 启动一个不带参数的Activity,并判断是否关闭当前页
     */
    fun startActivity(context: Context, cla: Class<*>, isFinish: Boolean) {
        val intent = Intent(context, cla)
        context.startActivity(intent)
        if (isFinish) {
            (context as Activity).finish()
        }
    }


    /**
     * 启动一个带参数的Activity,并判断是否关闭当前页
     * @param cls    Activity
     * @param extras 参数
     * @param isFinish 是否关闭当前页
     */
    fun startActivityWithExtras(context: Context, cls: Class<*>, extras: Bundle) {
        val intent = Intent(context, cls)
        intent.putExtras(extras)
        context.startActivity(intent)
    }

    /**
     * 启动一个带参数的Activity,并判断是否关闭当前页
     * @param cls    Activity
     * @param extras 参数
     * @param isFinish 是否关闭当前页
     */
    fun startActivityWithExtras(context: Context, cls: Class<*>, extras: Bundle, isFinish: Boolean) {
        val intent = Intent(context, cls)
        intent.putExtras(extras)
        context.startActivity(intent)
        if (isFinish) {
            (context as Activity).finish()
        }
    }


    fun isForeground(activity: Activity): Boolean {
        return isForeground(activity, activity.javaClass.name)
    }

    /**
     * 判断某个界面是否在前台

     * @param context   Context
     * @param className 界面的类名
     * @return 是否在前台显示
     */
    fun isForeground(context: Context?, className: String): Boolean {
        if (context == null || TextUtils.isEmpty(className))
            return false
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val list = am.getRunningTasks(1)
        if (list != null && list.size > 0) {
            val cpn = list[0].topActivity
            if (className == cpn.className)
                return true
        }
        return false
    }

}
