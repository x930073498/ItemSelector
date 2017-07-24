package com.x930073498.item_selector_lib.base;

import android.content.Context;

import java.util.List;

/**
 * Created by 930073498 on 2017/7/19.
 */

public interface OnCompletedListener {
    /**
     * 在主线程进行操作
     *
     * @param context  当前的
     * @param children
     * @return 表示是否finish掉选择Activity
     */
    boolean completed(Context context, List<DataChild> children);

    /**
     * 在子线程进行操作
     *
     * @param context
     * @param children
     * @return
     */
    boolean asynCompleted(Context context, List<DataChild> children);
}
