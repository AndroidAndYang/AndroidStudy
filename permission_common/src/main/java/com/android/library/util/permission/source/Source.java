/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.library.util.permission.source;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import java.lang.reflect.Method;

/**
 * @author YJZ
 *         date: 2017/10/23
 *         description：
 */
public abstract class Source {

    public abstract Context getContext();

    public abstract void startActivity(Intent intent);

    public abstract void startActivityForResult(Intent intent, int requestCode);

    /**
     * Show permissions rationale?
     */
    public final boolean isShowRationalePermission(String permission)
    {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
        {
            return false;
        }

        PackageManager packageManager = getContext().getPackageManager();
        Class<?> pkManagerClass = packageManager.getClass();
        try
        {
            Method method = pkManagerClass.getMethod("shouldShowRequestPermissionRationale", String.class);
            if (!method.isAccessible())
            {
                method.setAccessible(true);
            }
            return (boolean) method.invoke(packageManager, permission);
        }
        catch (Exception ignored)
        {
            return false;
        }
    }

}
