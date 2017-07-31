package com.x930073498.item_selector_lib.base.bridge;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;

import com.mvvm.x930073498.library.BaseItem;
import com.x930073498.item_selector_lib.BR;
import com.x930073498.item_selector_lib.R;
import com.x930073498.item_selector_lib.base.Constants;
import com.x930073498.item_selector_lib.base.DataChild;
import com.x930073498.item_selector_lib.base.DataGroup;
import com.x930073498.item_selector_lib.databinding.LayoutItemGroupItemBinding;

/**
 * Created by 930073498 on 2017/7/24.
 */

public class GroupItem<V extends DataChild, T extends DataGroup<V>> implements BaseItem {
    private boolean isAnimate = false;

    private T group;
    private boolean isExpand = true;
    private Drawable groupIcon;
    private Drawable expandFlagIcon;


    public T getGroup() {
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
        isExpand = !isExpand;
        sendBroadcast(view.getContext());
        startAnimation(view.findViewById(R.id.iv_flag));
    }


    private void startAnimation(View view) {
        Animator animator;
        if (!isExpand) {
            animator = ObjectAnimator.ofFloat(view, "Rotation", 0, 180f).setDuration(ActivityViewModel.duration);
        } else {
            animator = ObjectAnimator.ofFloat(view, "Rotation", 180f, 0).setDuration(ActivityViewModel.duration);
        }
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                isAnimate = false;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isAnimate = false;
            }
        });
        animator.start();
    }

    public GroupItem(Context context, T group, Drawable iconDropDown) {
        this.group = group;
        expandFlagIcon = iconDropDown;
        groupIcon = group.provideIcon(context);
    }

    public Drawable provideFlagDrawable() {
        return expandFlagIcon;
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
        LayoutItemGroupItemBinding binding = (LayoutItemGroupItemBinding) viewDataBinding;
        if (isExpand) {
            binding.ivFlag.setRotation(0f);
        } else {
            binding.ivFlag.setRotation(180f);
        }
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
                .sendBroadcast(new Intent(Constants.ACTION_GROUP).putExtra(Constants.KEY_DATA, group).putExtra(Constants.KEY_BOOLEAN, isExpand()));
    }


    public CharSequence provideGroupName() {
        return group.provideName();
    }


    public Drawable provideGroupIcon() {
        return groupIcon;
    }

    @Override
    public String toString() {
        return "GroupItem{" +
                "group=" + group +
                '}';
    }
}
