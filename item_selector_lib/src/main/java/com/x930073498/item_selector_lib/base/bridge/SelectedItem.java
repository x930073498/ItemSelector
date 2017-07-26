package com.x930073498.item_selector_lib.base.bridge;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;

import com.mvvm.x930073498.library.BaseItem;
import com.x930073498.item_selector_lib.BR;
import com.x930073498.item_selector_lib.R;
import com.x930073498.item_selector_lib.base.Constants;
import com.x930073498.item_selector_lib.base.DataChild;
import com.x930073498.item_selector_lib.databinding.LayoutItemSelectedItemBinding;

/**
 * Created by 930073498 on 2017/7/24.
 */

public class SelectedItem implements BaseItem {
    private DataChild child;

    public void onClick(View view) {
        sendBroadcast(view.getContext());
    }

    private void sendBroadcast(Context context) {
        LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(Constants.ACTION_CHILD).putExtra(Constants.KEY_DATA, child).putExtra(Constants.KEY_BOOLEAN, true));
    }

    public DataChild getChild() {
        return child;
    }

    public void setChild(DataChild child) {
        this.child = child;
    }

    public SelectedItem( DataChild child) {
        this.child = child;
    }

    public CharSequence provideName() {
        return child.provideName();
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
        LayoutItemSelectedItemBinding binding= (LayoutItemSelectedItemBinding) viewDataBinding;
        if (binding!=null){
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
               binding.image.setRadius(dip2px(binding.image.getContext(),6));
            }
        }
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

    private  int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
