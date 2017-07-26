package com.x930073498.item_selector_lib.base;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Build;
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
    public final static String TAG = "ItemSelectorActivity";

    public static final int NO_UPPER = Integer.MAX_VALUE;
    public static final int NO_LOWER = -1;

    private static final int TYPE_GROUP = 0;
    private static final int TYPE_CHILD = 1;
    private static final String KEY_MAX = "max";
    private static final String KEY_MIN = "min";
    private static final String KEY = "key";
    private static final String KEY_TYPE = "type";
    private static final String KEY_TITLE = "title";
    private static SparseArray<List<DataChild>> childMap;
    private static SparseArray<List<DataGroup>> groupMap;
    private static SparseArray<OnCompletedListener> listeners;
    private List<DataChild> children;
    private List<DataGroup> groups;
    private OnCompletedListener listener;
    private int min, max;
    private CharSequence title;
    private int type = -1;
    private ActivityViewModel viewModel;


    public static void openActivity(Context context, OnCompletedListener listener, List<DataGroup> groups, CharSequence title, int min, int max) {
        if (context == null) return;
        if (groups == null || groups.size() == 0) {
            Toast.makeText(context, "没有获取到有效分组！", Toast.LENGTH_SHORT).show();
            return;
        }
        int hashcode = context.hashCode();
        if (groupMap == null) groupMap = new SparseArray<>();
        groupMap.put(hashcode, groups);
        if (listeners == null) listeners = new SparseArray<>();
        listeners.put(hashcode, listener);
        Intent intent = new Intent(context, ItemSelectorActivity.class);
        intent.putExtra(KEY, hashcode);
        intent.putExtra(KEY_TYPE, TYPE_GROUP);
        intent.putExtra(KEY_MAX, max);
        intent.putExtra(KEY_MIN, min);
        intent.putExtra(KEY_TITLE, title);
        context.startActivity(intent);
    }

    public static void openActivity(Context context, OnCompletedListener listener, List<DataGroup> groups, CharSequence title) {
        openActivity(context, listener, groups, title, NO_LOWER, NO_UPPER);
    }

    public static void openActivity(Context context, OnCompletedListener listener, List<DataGroup> groups) {
        openActivity(context, listener, groups, "选择");
    }

    public static void openActivity(Context context, List<DataChild> children, OnCompletedListener listener, CharSequence title, int min, int max) {
        if (context == null) return;
        if (children == null || children.size() == 0) {
            Toast.makeText(context, "没有获取到有效分组！", Toast.LENGTH_SHORT).show();
            return;
        }
        int hashcode = context.hashCode();
        if (childMap == null) childMap = new SparseArray<>();
        childMap.put(hashcode, children);
        if (listeners == null) listeners = new SparseArray<>();
        listeners.put(hashcode, listener);
        Intent intent = new Intent(context, ItemSelectorActivity.class);
        intent.putExtra(KEY, hashcode);
        intent.putExtra(KEY_MAX, max);
        intent.putExtra(KEY_TYPE, TYPE_CHILD);
        intent.putExtra(KEY_MIN, min);
        intent.putExtra(KEY_TITLE, title);
        context.startActivity(intent);
    }

    public static void openActivity(Context context, List<DataChild> children, OnCompletedListener listener, CharSequence title) {
        openActivity(context, children, listener, title, NO_LOWER, NO_UPPER);
    }

    public static void openActivity(Context context, List<DataChild> children, OnCompletedListener listener) {
        openActivity(context, children, listener, "选择");
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        parseIntent(getIntent());

        super.onCreate(savedInstanceState);
        ActivitySelectorItemBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_selector_item);

        if (type == TYPE_CHILD)
            viewModel = new ActivityViewModel(this, new DataPresenter(this, children), title, max, min, listener);
        else {
            viewModel = new ActivityViewModel(this, new DataPresenter(groups, this), title, max, min, listener);
        }
        binding.setData(viewModel);
    }

    private void parseIntent(Intent intent) {
        if (intent == null) return;
        int key = intent.getIntExtra(KEY, -1);
        type = intent.getIntExtra(KEY_TYPE, -1);
        if (type == -1) return;

        if (childMap != null && type == TYPE_CHILD)
            children = childMap.get(key);
        if (groupMap != null && type == TYPE_GROUP)
            groups = groupMap.get(key);
        if (listeners != null) {
            listener = listeners.get(key);
        }
        min = intent.getIntExtra(KEY_MIN, NO_LOWER);
        max = intent.getIntExtra(KEY_MAX, NO_UPPER);
        title = intent.getCharSequenceExtra(KEY_TITLE);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (viewModel != null) viewModel.onDestroy();
    }


}
