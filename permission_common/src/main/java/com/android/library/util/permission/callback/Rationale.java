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
package com.android.library.util.permission.callback;

import android.content.Context;

import java.util.List;

/**
 * @author YJZ
 *         date: 2017/10/26
 *         description：
 */
public interface Rationale {

    /**
     * Show rationale of permissions to user.
     *
     * @param context     context.
     * @param permissions show rationale permissions.
     * @param executor    executor.
     */
    void showRationale(Context context, List<String> permissions, RequestExecutor executor);
}