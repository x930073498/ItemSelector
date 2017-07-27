package com.x930073498.item_selector_lib.base.presenter;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableArrayList;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

import com.mvvm.x930073498.library.BaseAdapter;
import com.mvvm.x930073498.library.BaseItem;
import com.x930073498.item_selector_lib.base.Constants;
import com.x930073498.item_selector_lib.base.DataChild;
import com.x930073498.item_selector_lib.base.DataGroup;
import com.x930073498.item_selector_lib.base.OnCompletedListener;
import com.x930073498.item_selector_lib.base.bridge.ChildItem;
import com.x930073498.item_selector_lib.base.bridge.GroupItem;
import com.x930073498.item_selector_lib.base.bridge.SelectedItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 930073498 on 2017/7/24.
 */

public class Controller<CHILD extends DataChild,GROUP extends DataGroup<CHILD>> {

    private List<BaseItem> showItems = new ObservableArrayList<>();
    private ArrayMap<GroupItem<CHILD,GROUP>, List<ChildItem<CHILD>>> deleteItems = new ArrayMap<>();
    private List<SelectedItem<CHILD>> selectedItems = new ObservableArrayList<>();
    private DataPresenter<CHILD,GROUP> presenter;
    private BaseAdapter mainAdapter;

    public Controller(DataPresenter<CHILD,GROUP> presenter, BaseAdapter mainAdapter, BaseAdapter selectedAdapter) {
        this.presenter = presenter;
        showItems.addAll(presenter.getOriginalItems());
        this.mainAdapter = mainAdapter;
        mainAdapter.setData(showItems);
        selectedAdapter.setData(selectedItems);
    }

    private void showOriginal() {
        deleteItems.clear();
        showItems = new ObservableArrayList<>();
        showItems.addAll(presenter.getOriginalItems());
        mainAdapter.setData(showItems);
    }

    public void showSearch(GROUP group, String name) {

        if (group == null && (TextUtils.isEmpty(name) || TextUtils.isEmpty(name.trim()))) {
            showOriginal();
        } else {
            search(group, name);
            mainAdapter.setData(showItems);
        }
    }

    private List<BaseItem> searchGroup(GROUP group) {
        List<BaseItem> original = presenter.getOriginalItems();
        presenter.resetGroupExpand();
        if (group == null) return original;
        List<BaseItem> list = new ArrayList<>();
        GroupItem<CHILD,GROUP> item = presenter.getGroupItem(group);
        list.add(item);
        if (item == null) return list;
        list.addAll(presenter.getChildren(item));
        return list;
    }

    private List<BaseItem> search(List<BaseItem> original, String name) {
        if (original == null) return new ArrayList<>();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(name.trim())) return original;
        GroupItem item = null;
        ChildItem childItem;
        boolean hasQualifiedChild = false;
        ArrayList<BaseItem> list = new ArrayList<>();
        ArrayList<BaseItem> temp = new ArrayList<>();
        for (BaseItem baseItem : original
                ) {
            if (baseItem == null) continue;
            if (baseItem instanceof GroupItem) {
                if (!baseItem.equals(item)) {
                    if (hasQualifiedChild) {
                        list.add(item);
                        list.addAll(temp);
                    }
                    hasQualifiedChild = false;
                    temp.clear();
                    item = (GroupItem) baseItem;
                }
            } else {
                if (baseItem instanceof ChildItem) {
                     childItem= (ChildItem) baseItem;
                    if (childItem.provideItemName() == null) continue;
                    if (childItem.provideItemName().toString().contains(name)) {
                        temp.add(childItem);
                        hasQualifiedChild = true;
                    }
                }
            }

        }
        if (hasQualifiedChild) {
            list.add(item);
            list.addAll(temp);
        }
        return list;
    }


    private void search(GROUP group, String name) {
        List<BaseItem> list = search(searchGroup(group), name);
        showItems = new ObservableArrayList<>();
        showItems.addAll(list);

    }

    public void collapse(GROUP group) {
        GroupItem<CHILD,GROUP> item = presenter.getGroupItem(group);
        int index = showItems.indexOf(item);
        ArrayList<ChildItem<CHILD>> childItems = new ArrayList<>();
        for (int i = index + 1; i < showItems.size(); i++) {
            BaseItem baseItem = showItems.get(i);
            if (baseItem == null) continue;
            if (baseItem instanceof GroupItem) break;
            if (baseItem instanceof ChildItem) {
                childItems.add((ChildItem) baseItem);
            }
        }
        deleteItems.put(item, childItems);
        showItems.removeAll(childItems);
        mainAdapter.notifyItemRangeRemoved(index + 1, childItems.size());

    }

    public void expand(GROUP group) {
        GroupItem<CHILD,GROUP> item = presenter.getGroupItem(group);
        int index = showItems.indexOf(item);
        List<ChildItem<CHILD>> children = deleteItems.remove(item);
        if (children == null) return;
        showItems.addAll(index + 1, children);
    }

    public void select(CHILD child) {
        ChildItem<CHILD> childItem = presenter.getChildItem(child);
        if (childItem != null) {
            childItem.setSelected(true);
        }
        SelectedItem<CHILD> item = getSelectedItem(child);
        if (item == null) {
            SelectedItem<CHILD> selectedItem = new SelectedItem<>(child);
            selectedItems.add(selectedItem);
        } else {
            selectedItems.add(item);
        }
        sendSelectStatusBroadcast();
    }

    public void unSelect(CHILD child) {
        ChildItem<CHILD> childItem = presenter.getChildItem(child);
        if (child == null) return;
        childItem.setSelected(false);
        SelectedItem<CHILD> item = getSelectedItem(child);
        if (item == null) return;
        selectedItems.remove(item);
        sendSelectStatusBroadcast();
    }

    private void sendSelectStatusBroadcast() {
        if (presenter == null) return;
        Context context = presenter.getContext();
        if (context == null) return;
        LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(Constants.ACTION_SUMBIT_STATUS_NOTIFY));
    }

    private SelectedItem<CHILD> getSelectedItem(CHILD child) {
        if (child == null) return null;
        if (selectedItems == null) return null;
        for (SelectedItem<CHILD> item : selectedItems
                ) {
            if (item == null) continue;
            if (item.getChild() == null) continue;
            if (item.getChild().equals(child)) return item;
        }
        return null;
    }

    public void submit(OnCompletedListener<CHILD> listener) {
        List<CHILD> children = new ArrayList<>();
        CHILD child;
        for (SelectedItem<CHILD> item : selectedItems
                ) {
            if (item == null) continue;
             child= item.getChild();
            if (child == null) return;
            children.add(child);
        }
        if (listener != null) listener.completed(children);
    }

    public void onDestroy() {
        if (presenter != null) presenter.onDestroy();
        if (showItems != null) {
            showItems.clear();
            showItems = null;
        }
        if (deleteItems != null) {
            deleteItems.clear();
            deleteItems = null;
        }
        if (selectedItems != null) {
            selectedItems.clear();
            selectedItems = null;
        }
    }

}
