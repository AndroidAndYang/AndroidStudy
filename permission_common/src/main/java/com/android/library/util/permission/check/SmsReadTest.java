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

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.provider.Telephony;
import android.support.annotation.RequiresApi;

import com.android.library.util.permission.callback.PermissionTest;

/**
 * @author YJZ
 *         date: 2017/10/23
 *         description：
 */
public class SmsReadTest implements PermissionTest {

    private ContentResolver mResolver;

    public SmsReadTest(Context context)
    {
        mResolver = context.getContentResolver();
    }

    @RequiresApi (api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean test() throws Throwable
    {
        String[] projection = new String[]{Telephony.Sms._ID, Telephony.Sms.ADDRESS, Telephony.Sms.PERSON, Telephony.Sms.BODY};
        Cursor cursor = mResolver.query(Telephony.Sms.CONTENT_URI, projection, null, null, null);
        if (cursor != null)
        {
            try
            {
                CursorTest.read(cursor);
            }
            finally
            {
                cursor.close();
            }
            return true;
        } else
        {
            return false;
        }
    }
}