package com.x930073498.item_selector_lib.base.bridge;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ViewDataBinding;
import android.graphics.drawable.Drawable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;

import com.mvvm.x930073498.library.BaseItem;
import com.x930073498.item_selector_lib.BR;
import com.x930073498.item_selector_lib.R;
import com.x930073498.item_selector_lib.base.Constants;
import com.x930073498.item_selector_lib.base.DataChild;
import com.x930073498.item_selector_lib.base.DataGroup;


/**
 * Created by 930073498 on 2017/7/24.
 */

public class ChildItem extends BaseObservable implements BaseItem  {
    private Context context;

    private DataChild child;
    private boolean isSelected = false;


    public DataChild getChild() {
        return child;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
        notifyPropertyChanged(BR.selected);
    }

    @Bindable
    public boolean isSelected() {
        return isSelected;
    }

    public void onClick(View v) {
//        boolean flag = isSelected;
//        if (flag) {
//            setSelected(false);
//        } else {
//            setSelected(true);
//        }
        sendBroadcast(v.getContext());
    }

    public ChildItem(Context context, DataChild child) {
        this.child = child;
        this.context = context;
    }


    @Override
    public int getLayoutId() {
        return R.layout.layout_item_child_item;
    }

    @Override
    public int getVariableId() {
        return BR.child;
    }

    @Override
    public void onBindView(ViewDataBinding viewDataBinding, int i) {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChildItem item = (ChildItem) o;

        return child.equals(item.child);

    }

    @Override
    public int hashCode() {
        return child.hashCode();
    }

    private void sendBroadcast(Context context) {
        LocalBroadcastManager.getInstance(context)
                .sendBroadcast(new Intent(Constants.ACTION_CHILD).putExtra(Constants.KEY_DATA, child).putExtra(Constants.KEY_BOOLEAN, isSelected()));
    }


    public DataGroup provideGroup() {
        return child.provideGroup();
    }


    public CharSequence provideItemName() {
        return child.provideName();
    }

    public CharSequence provideItemDescription() {
        return child.provideDescription();
    }


    public Drawable provideItemNameIcon() {
        return child.provideNameIcon(context);
    }


    public Drawable provideItemDescriptionIcon() {
        return child.provideDescriptionIcon(context);
    }

    @Override
    public String toString() {
        return "ChildItem{" +
                "child=" + child +
                '}';
    }
}
