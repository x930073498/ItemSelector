package com.x930073498.item_selector_lib.base;

import android.content.Context;

import java.util.List;

/**
 * Created by 930073498 on 2017/7/19.
 */

public interface OnCompletedListener<T extends DataChild> {
    void completed( List<T> children);

}
