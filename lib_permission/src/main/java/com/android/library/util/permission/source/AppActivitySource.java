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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * @author YJZ
 *         date: 2017/10/26
 *         description：
 */
public class AppActivitySource extends Source {

    private Activity mActivity;

    public AppActivitySource(Activity activity) {
        this.mActivity = activity;
    }

    @Override
    public Context getContext() {
        return mActivity;
    }

    @Override
    public void startActivity(Intent intent) {
        mActivity.startActivity(intent);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        mActivity.startActivityForResult(intent, requestCode);
    }
}
