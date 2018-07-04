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

import android.os.Environment;

import com.android.library.util.permission.callback.PermissionTest;

import java.io.File;

/**
 * @author YJZ
 *         date: 2017/10/23
 *         description：
 */
public class StorageReadTest implements PermissionTest {

    @Override
    public boolean test() throws Throwable
    {
        File directory = Environment.getExternalStorageDirectory();
        if (directory.exists() && directory.canRead())
        {
            long modified = directory.lastModified();
            String[] pathList = directory.list();
            return modified > 0 && pathList != null;
        }
        return false;
    }
}