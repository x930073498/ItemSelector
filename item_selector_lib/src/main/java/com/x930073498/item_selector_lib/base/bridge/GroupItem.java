package com.x930073498.item_selector_lib.base.bridge;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
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
    DataGroup group;
    private boolean isExpand = true;
    private LayoutItemGroupItemBinding binding;


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
        setExpand(!isExpand());
        sendBroadcast(view.getContext());
        startAnimation();
    }

    private void startAnimation() {
        Animator animator;
        if (isExpand()) {
            animator = ObjectAnimator.ofFloat(binding.ivFlag, "Rotation", 0, 180f).setDuration(ActivityViewModel.duration);
        } else {
            animator = ObjectAnimator.ofFloat(binding.ivFlag, "Rotation", 180f, 0).setDuration(ActivityViewModel.duration);
        }
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isAnimate = false;
            }
        });
        animator.start();
    }

    public GroupItem(DataGroup group) {
        this.group = group;
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
        binding = (LayoutItemGroupItemBinding) viewDataBinding;
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
}
