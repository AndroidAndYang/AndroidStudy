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
package com.android.library.util.permission.check;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.android.library.util.permission.callback.PermissionChecker;

import java.util.Arrays;
import java.util.List;

/**
 * @author YJZ
 *         date: 2017/10/23
 *         description：
 */
public final class StandardChecker implements PermissionChecker {

    @Override
    public boolean hasPermission(@NonNull Context context, @NonNull String... permissions)
    {
        return hasPermission(context, Arrays.asList(permissions));
    }

    @Override
    public boolean hasPermission(@NonNull Context context, @NonNull List<String> permissions)
    {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
        {
            return true;
        }

        for (String permission : permissions)
        {
            int result = context.checkPermission(permission, android.os.Process.myPid(), android.os.Process.myUid());
            if (result == PackageManager.PERMISSION_DENIED)
            {
                return false;
            }

            String op = AppOpsManager.permissionToOp(permission);
            if (TextUtils.isEmpty(op))
            {
                continue;
            }

            AppOpsManager appOpsManager = context.getSystemService(AppOpsManager.class);
            result = appOpsManager.noteProxyOp(op, context.getPackageName());
            if (result != AppOpsManager.MODE_ALLOWED)
            {
                return false;
            }

        }
        return true;
    }
}