package com.seabig.moduledemo.common.util;

import android.support.v7.app.AppCompatActivity;

import java.util.LinkedList;
import java.util.List;

/**
 * @author YJZ
 *         date 2018/3/10
 */

public class ActivityContainer {

    private ActivityContainer()
    {

    }

    private static ActivityContainer instance = new ActivityContainer();

    private static List<AppCompatActivity> activityStack = new LinkedList<>();

    public static ActivityContainer getInstance()
    {
        return instance;
    }

    public void addActivity(AppCompatActivity aty)
    {
        activityStack.add(aty);
    }

    public void removeActivity(AppCompatActivity aty)
    {
        activityStack.remove(aty);
    }

    /**
     * 结束所有的Activity
     */
    public void finishAllActivity()
    {
        for (int i = 0, size = activityStack.size(); i < size; i++)
        {
            if (null != activityStack.get(i))
            {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }
}