package com.x930073498.item_selector_lib.base.bridge;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;

import com.mvvm.x930073498.library.BaseItem;
import com.x930073498.item_selector_lib.BR;
import com.x930073498.item_selector_lib.R;
import com.x930073498.item_selector_lib.base.DataChild;

/**
 * Created by 930073498 on 2017/7/24.
 */

public class SelectedItem implements BaseItem {
    private DataChild child;

    public void onClick(View view) {
        sendBroadcast(view.getContext());
    }

    private void sendBroadcast(Context context) {
        LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(ChildItem.ACTION_CHILD).putExtra(ActivityViewModel.KEY_DATA, child).putExtra(ActivityViewModel.KEY_BOOLEAN, true));
    }

    public DataChild getChild() {
        return child;
    }

    public void setChild(DataChild child) {
        this.child = child;
    }

    public SelectedItem(DataChild child) {
        this.child = child;
    }

    public CharSequence provideName() {
        return child.provideItemName();
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_item_selected_item;
    }

    @Override
    public int getVariableId() {
        return BR.selected;
    }

    @Override
    public void onBindView(ViewDataBinding viewDataBinding, int position) {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SelectedItem that = (SelectedItem) o;

        return child.equals(that.child);

    }

    @Override
    public int hashCode() {
        return child.hashCode();
    }

    @Override
    public String toString() {
        return "SelectedItem{" +
                "child=" + child +
                '}';
    }
}
