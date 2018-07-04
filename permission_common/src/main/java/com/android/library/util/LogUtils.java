package com.android.library.util;

import android.util.Log;

/**
 * @author YJZ
 * Time: 2017/6/1
 * Desc: 日志工具类
 */

public class LogUtils {

    /**
     * 类名
     */
    private static String className = "TAG";
    /**
     * 方法名
     */
    private static String methodName;
    /**
     * 行数
     */
    private static int lineNumber;
    /**
     * 设置为false表示为正式版不打印log
     */
    private static boolean isDebuggable = true;

    private LogUtils()
    {
        /* Protect from instantiations */
    }

    private static String createLog(String log)
    {
        StringBuffer buffer = new StringBuffer()
                // 显示在类里面的那个方法
                .append(methodName)
                .append("[ ")
                // 显示类名
                .append(className)
                .append(":")
                // 显示行号
                .append(lineNumber)
                .append(" ] ")
                .append(log);
        return buffer.toString();
    }

    private static void getMethodNames(StackTraceElement[] sElements)
    {
        methodName = sElements[1].getMethodName();
        lineNumber = sElements[1].getLineNumber();
    }

    public static void e(String message)
    {
        if (!isDebuggable)
        {
            return;
        }
        // Throwable instance must be created before any methods
        getMethodNames(new Throwable().getStackTrace());
        Log.e(className, createLog(message));
    }


    public static void i(String message)
    {
        if (!isDebuggable)
        {
            return;
        }

        getMethodNames(new Throwable().getStackTrace());
        Log.i(className, createLog(message));
    }

    public static void d(String message)
    {
        if (!isDebuggable)
        {
            return;
        }
        getMethodNames(new Throwable().getStackTrace());
        Log.d(className, createLog(message));
    }

    public static void v(String message)
    {
        if (!isDebuggable)
        {
            return;
        }
        getMethodNames(new Throwable().getStackTrace());
        Log.v(className, createLog(message));
    }

    public static void w(String message)
    {
        if (!isDebuggable)
        {
            return;
        }
        getMethodNames(new Throwable().getStackTrace());
        Log.w(className, createLog(message));
    }

}
