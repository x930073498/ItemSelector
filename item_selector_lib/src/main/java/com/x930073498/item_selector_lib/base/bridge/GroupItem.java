package com.x930073498.item_selector_lib.base.bridge;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;

import com.mvvm.x930073498.library.BaseItem;
import com.x930073498.item_selector_lib.BR;
import com.x930073498.item_selector_lib.R;
import com.x930073498.item_selector_lib.base.DataGroup;
import com.x930073498.item_selector_lib.databinding.LayoutItemGroupItemBinding;

/**
 * Created by 930073498 on 2017/7/24.
 */

public class GroupItem implements BaseItem {
    private boolean isAnimate = false;
    public final static String ACTION_GROUP = "action_group";
    private DataGroup group;
    private boolean isExpand = true;
    private Drawable groupIcon;
    private Drawable expandFlagIcon, collapseFlagIcon;


    public DataGroup getGroup() {
        return group;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
    }

    public void onClick(View view) {
        if (isAnimate) return;
        isAnimate = true;
        sendBroadcast(view.getContext());
        startAnimation(view.findViewById(R.id.iv_flag));
    }


    private void startAnimation(View view) {
        Animator animator;
        if (isExpand) {
            animator = ObjectAnimator.ofFloat(view, "Rotation", 0, 180f).setDuration(ActivityViewModel.duration);
        } else {
            animator = ObjectAnimator.ofFloat(view, "Rotation", 180f, 0).setDuration(ActivityViewModel.duration);
        }
        animator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isExpand = !isExpand;
                isAnimate = false;
            }
        });
        animator.start();
    }

    public GroupItem(Context context, DataGroup group) {
        this.group = group;
        expandFlagIcon = ContextCompat.getDrawable(context, R.drawable.la1);
        collapseFlagIcon = ContextCompat.getDrawable(context, R.drawable.la2);
        groupIcon = group.provideGroupIcon(context);
    }

    public Drawable provideFlagDrawable() {
        if (isExpand) {
            return expandFlagIcon;
        } else {
            return collapseFlagIcon;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_item_group_item;
    }

    @Override
    public int getVariableId() {
        return BR.group;
    }

    @Override
    public void onBindView(ViewDataBinding viewDataBinding, int i) {

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroupItem item = (GroupItem) o;

        return group.equals(item.group);

    }

    @Override
    public int hashCode() {
        return group.hashCode();
    }

    private void sendBroadcast(Context context) {
        LocalBroadcastManager.getInstance(context)
                .sendBroadcast(new Intent(ACTION_GROUP).putExtra(ActivityViewModel.KEY_DATA, group).putExtra(ActivityViewModel.KEY_BOOLEAN, isExpand()));
    }


    public CharSequence provideGroupName() {
        return group.provideGroupName();
    }


    public Drawable provideGroupIcon() {
        return groupIcon;
    }


}
