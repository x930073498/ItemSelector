package com.x930073498.item_selector_lib.base;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 930073498 on 2017/7/19.
 */

public interface DataGroup extends Serializable {

    /**
     * 组别唯一id，作为唯一标识符
     *
     * @return
     */
    @NonNull
    CharSequence provideGroupId();

    /**
     * 组别名称
     *
     * @return
     */
    CharSequence provideGroupName();

    /**
     * 组别标识图标
     *
     * @return
     */
    Drawable provideGroupIcon(Context context);

}
