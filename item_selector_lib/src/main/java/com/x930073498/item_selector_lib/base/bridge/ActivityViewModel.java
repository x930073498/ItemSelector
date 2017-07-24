package com.x930073498.item_selector_lib.base.bridge;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.mvvm.x930073498.library.BaseAdapter;
import com.mvvm.x930073498.library.BaseItem;
import com.x930073498.item_selector_lib.base.DataChild;
import com.x930073498.item_selector_lib.base.DataGroup;
import com.x930073498.item_selector_lib.base.OnCompletedListener;
import com.x930073498.item_selector_lib.base.presenter.Controller;
import com.x930073498.item_selector_lib.base.presenter.DataPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 930073498 on 2017/7/24.
 */

public class ActivityViewModel {
    public final static String KEY_DATA = "key_data";
    public final static String KEY_BOOLEAN = "key_boolean";
    private CharSequence title;
    private int max;
    private int min;
    private OnCompletedListener listener;
    private Context context;
    private LocalBroadcastManager manager;
    public final static long duration = 750;
    private SelectReceiver selectReceiver = new SelectReceiver();
    private DataGroup currentGroup = null;
    private String searchText;

    private ExpandReceiver expandReceiver = new ExpandReceiver();
    private Controller controller;
    private BaseAdapter mainAdapter = new BaseAdapter();
    private BaseAdapter selectedAdapter = new BaseAdapter();
    private List<String> dialogGroupNames = new ArrayList<>();
    private int currentIndex = 0;
    private DataPresenter presenter;


    public ActivityViewModel(Context context, DataPresenter presenter, CharSequence title, int max, int min, OnCompletedListener listener) {
        this.context = context;
        this.presenter = presenter;
        this.title = title;
        this.max = max;
        this.min = min;
        this.listener = listener;
        controller = new Controller(presenter, mainAdapter, selectedAdapter);
        registerReceiver();
        parseGroupNames(presenter);
    }

    private void parseGroupNames(DataPresenter presenter) {
        dialogGroupNames.add("全部");
        if (presenter.getGroups() == null) return;
        for (DataGroup group : presenter.getGroups()
                ) {
            if (group == null) continue;
            dialogGroupNames.add(group.provideGroupName().toString());
        }
    }

    private void registerReceiver() {
        if (manager == null) manager = LocalBroadcastManager.getInstance(context);
        manager.registerReceiver(selectReceiver, new IntentFilter(ChildItem.ACTION_CHILD));
        manager.registerReceiver(expandReceiver, new IntentFilter(GroupItem.ACTION_GROUP));
    }

    public CharSequence getTitle() {
        return title;
    }

    public void search(View view) {
        controller.showSearch(currentGroup, searchText);
    }

    public void showChooseDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        String[] array = new String[dialogGroupNames.size()];
        builder.setSingleChoiceItems(dialogGroupNames.toArray(array), currentIndex, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                currentIndex = which;
                int temp = which - 1;
                if (temp < 0) currentGroup = null;
                else currentGroup = presenter.getGroups().get(temp);
            }
        });
        builder.show();
    }


    public void submit(View view) {

    }

    public CharSequence getCurrentGroupName() {
        if (currentGroup == null) return "全部";
        return currentGroup.provideGroupName();
    }

    private class SelectReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            DataChild child = (DataChild) intent.getSerializableExtra(KEY_DATA);
            if (child == null) return;
            boolean isSelected = intent.getBooleanExtra(KEY_BOOLEAN, false);

        }
    }

    private class ExpandReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }
}
