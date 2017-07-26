package com.x930073498.item_selector_lib.base.bridge;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mvvm.x930073498.library.BaseAdapter;
import com.x930073498.item_selector_lib.BR;
import com.x930073498.item_selector_lib.base.Constants;
import com.x930073498.item_selector_lib.base.DataChild;
import com.x930073498.item_selector_lib.base.DataGroup;
import com.x930073498.item_selector_lib.base.ItemSelectorActivity;
import com.x930073498.item_selector_lib.base.OnCompletedListener;
import com.x930073498.item_selector_lib.base.presenter.Controller;
import com.x930073498.item_selector_lib.base.presenter.DataPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 930073498 on 2017/7/24.
 */

public class ActivityViewModel extends BaseObservable {
    private CharSequence title;
    private int max;
    private int min;
    private OnCompletedListener listener;
    private Context context;
    private LocalBroadcastManager manager;
    public final static long duration = 750;
    private DataGroup currentGroup = null;
    private CharSequence currentGroupName = "全部";
    private String searchText;
    private ExpandReceiver expandReceiver = new ExpandReceiver();
    private SelectReceiver selectReceiver = new SelectReceiver();
    private SubmitStatusReceiver submitStatusReceiver = new SubmitStatusReceiver();
    private Controller controller;
    private BaseAdapter mainAdapter = new BaseAdapter();
    private BaseAdapter selectedAdapter = new BaseAdapter();
    private List<String> dialogGroupNames = new ArrayList<>();
    private RecyclerView.LayoutManager mainLayoutManger;
    private RecyclerView.LayoutManager selectedLayoutManager;
    private int currentIndex = 0;
    private DataPresenter presenter;
    private boolean submitAble = false;


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
        setSubmitAble(getInternalSubmitStatus());
        registerAdapterNotify();
    }

    private void registerAdapterNotify(){
        mainAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
            }
        });

        selectedAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
//                if (selectedLayoutManager!=null)selectedLayoutManager.scrollToPosition(positionStart+itemCount);
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
//                if (selectedLayoutManager!=null)selectedLayoutManager.scrollToPosition(positionStart+itemCount);
            }
        });
    }

    private boolean getInternalSubmitStatus() {
        if (max == ItemSelectorActivity.NO_UPPER) {
            max = Integer.MAX_VALUE;
        }
        min = Math.max(0, min);
        max = Math.max(min, max);
        if (max == 0) return true;
        int selectedCount = selectedAdapter.getCount();
        if (selectedCount >= min && selectedCount <= max) return true;
        return false;
    }

    @Bindable
    public boolean isSubmitAble() {
        return submitAble;
    }


    public void finishActivity(View view) {
        if (context != null && context instanceof Activity) {
            ((Activity) context).finish();
        }
    }

    public void setSubmitAble(boolean submitAble) {
        this.submitAble = submitAble;
        notifyPropertyChanged(BR.submitAble);
    }

    private void parseGroupNames(DataPresenter presenter) {
        dialogGroupNames.add("全部");
        if (presenter.getGroups() == null) return;
        for (DataGroup group : presenter.getGroups()
                ) {
            if (group == null) continue;
            dialogGroupNames.add(group.provideName().toString());
        }
    }

    private void registerReceiver() {
        if (manager == null) manager = LocalBroadcastManager.getInstance(context);
        manager.registerReceiver(selectReceiver, new IntentFilter(Constants.ACTION_CHILD));
        manager.registerReceiver(expandReceiver, new IntentFilter(Constants.ACTION_GROUP));
        manager.registerReceiver(submitStatusReceiver, new IntentFilter(Constants.ACTION_SUMBIT_STATUS_NOTIFY));
    }

    public CharSequence getTitle() {
        return title;
    }

    public void search(View view) {
        controller.showSearch(currentGroup, searchText);
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public void showChooseDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        String[] array = new String[dialogGroupNames.size()];
        builder.setSingleChoiceItems(dialogGroupNames.toArray(array), currentIndex, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (currentIndex == which) {
                    dialog.dismiss();
                    return;
                }
                currentIndex = which;
                int temp = which - 1;
                if (temp < 0) {
                    currentGroup = null;
                    setCurrentGroupName("全部");
                } else {
                    currentGroup = presenter.getGroups().get(temp);
                    setCurrentGroupName(currentGroup.provideName());
                }
                dialog.dismiss();
            }
        });
        builder.show();
    }


    private void expand(DataGroup group, boolean expand) {
        if (expand) {
            controller.expand(group);
        } else {
            controller.collapse(group);
        }
    }

    public void submit(View view) {
        if (submitAble){
            controller.submit(listener);
            finishActivity(view);
        }
    }

    private void selected(DataChild child, boolean selected) {
        if (!selected) {
            controller.select(child);
        } else {
            controller.unSelect(child);
        }
    }

    @Bindable
    public CharSequence getCurrentGroupName() {
        return currentGroupName;
    }

    public void setCurrentGroupName(CharSequence currentGroupName) {
        this.currentGroupName = currentGroupName;
        notifyPropertyChanged(BR.currentGroupName);
    }


    private class SelectReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            DataChild child = (DataChild) intent.getSerializableExtra(Constants.KEY_DATA);
            if (child == null) return;
            boolean isSelected = intent.getBooleanExtra(Constants.KEY_BOOLEAN, false);
            selected(child, isSelected);
        }
    }

    private class ExpandReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean expand = intent.getBooleanExtra(Constants.KEY_BOOLEAN, false);
            DataGroup group = (DataGroup) intent.getSerializableExtra(Constants.KEY_DATA);
            if (group == null) return;
            expand(group, expand);
        }
    }

    private class SubmitStatusReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            setSubmitAble(getInternalSubmitStatus());
        }
    }


    public BaseAdapter provideMainAdapter() {
        return mainAdapter;
    }

    public BaseAdapter provideSelectedAdapter() {
        return selectedAdapter;
    }

    public RecyclerView.ItemAnimator provideMainItemAnimator() {
        return new DefaultItemAnimator();
    }

    public RecyclerView.ItemAnimator provideSelectedItemAnimator() {
        return new DefaultItemAnimator();
    }

    public RecyclerView.LayoutManager provideMainLayoutManager() {
        return mainLayoutManger==null?(mainLayoutManger=new LinearLayoutManager(context)):mainLayoutManger;
    }

    public RecyclerView.LayoutManager provideSelectedLayoutManager() {
        return selectedLayoutManager==null? new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true):selectedLayoutManager;
    }

    public void onDestroy() {
        unregisterReceiver();
        if (presenter != null) presenter.onDestroy();
        if (controller != null) controller.onDestroy();
    }

    private void unregisterReceiver() {
        if (manager != null) {
            manager.unregisterReceiver(expandReceiver);
            manager.unregisterReceiver(selectReceiver);
            manager.unregisterReceiver(submitStatusReceiver);
        }
    }

}
