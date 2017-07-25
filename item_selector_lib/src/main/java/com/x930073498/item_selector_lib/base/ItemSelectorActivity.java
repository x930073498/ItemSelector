package com.x930073498.item_selector_lib.base;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.widget.Toast;

import com.x930073498.item_selector_lib.R;
import com.x930073498.item_selector_lib.base.bridge.ActivityViewModel;
import com.x930073498.item_selector_lib.base.presenter.DataPresenter;
import com.x930073498.item_selector_lib.databinding.ActivitySelectorItemBinding;

import java.util.List;

/**
 * Created by 930073498 on 2017/7/24.
 */

public class ItemSelectorActivity extends AppCompatActivity {
    public static final int MAX = -2;
    public static final int MIN = -1;

    private static final String KEY_MAX = "max";
    private static final String KEY_MIN = "min";
    private static final String KEY = "key";
    private static final String KEY_TITLE = "title";
    private static SparseArray<List<DataChild>> map;
    private static SparseArray<OnCompletedListener> listeners;
    private List<DataChild> children;
    private OnCompletedListener listener;
    private int min, max;
    private CharSequence title;

    public static void openActivity(Context context, List<DataChild> children, OnCompletedListener listener, CharSequence title, int min, int max) {
        if (context == null) return;
        if (children == null || children.size() == 0) {
            Toast.makeText(context, "没有获取到有效分组！", Toast.LENGTH_SHORT).show();
            return;
        }
        int hashcode = context.hashCode();
        if (map == null) map = new SparseArray<>();
        map.put(hashcode, children);
        if (listeners == null) listeners = new SparseArray<>();
        listeners.put(hashcode, listener);
        Intent intent = new Intent(context, ItemSelectorActivity.class);
        intent.putExtra(KEY, hashcode);
        intent.putExtra(KEY_MAX, max);
        intent.putExtra(KEY_MIN, min);
        intent.putExtra(KEY_TITLE, title);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        parseIntent(getIntent());
        super.onCreate(savedInstanceState);
        ActivitySelectorItemBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_selector_item);
//        ActivitySelectorItemBinding binding =
//                DataBindingUtil.setContentView(this, R.layout.layout_item_group_item);
//                DataBindingUtil.setContentView(this, R.layout.layout_item_child_item);
        binding.setData(new ActivityViewModel(this,new DataPresenter(this,children), title, max, min, listener));
    }

    private void parseIntent(Intent intent) {
        if (intent == null) return;
        int key = intent.getIntExtra(KEY, -1);
        if (children != null)
            children = map.get(key);
        if (listeners!=null){
            listener=listeners.get(key);
        }
        min = intent.getIntExtra(KEY_MIN, MIN);
        max = intent.getIntExtra(KEY_MAX, MAX);
        title = intent.getCharSequenceExtra(KEY_TITLE);
    }
}
