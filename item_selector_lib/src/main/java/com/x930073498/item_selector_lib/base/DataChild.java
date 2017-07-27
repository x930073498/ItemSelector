package com.x930073498.item_selector_lib.base;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by 930073498 on 2017/7/19.
 */

public interface DataChild extends Serializable {
    /**
     * 说明子项所属的组别
     *
     * @return
     */
    <T extends DataGroup> T  provideGroup();

    /**
     * 子项名
     *
     * @return
     */
    CharSequence provideName();

    /**
     * 子项显示的描述（如电话号码）
     *
     * @return
     */
    CharSequence provideDescription();

    /**
     * 子项的唯一id，作为唯一标识符
     *
     * @return
     */
    @NonNull
    CharSequence provideId();

    /**
     * 子项名旁边的图标
     *
     * @return
     */
    Drawable provideNameIcon(Context context);

    /**
     * 描述旁的图标
     *
     * @return
     */
    Drawable provideDescriptionIcon(Context context);
}
