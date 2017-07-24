package com.x930073498.item_selector_lib.base.presenter;

import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

import com.mvvm.x930073498.library.BaseAdapter;
import com.mvvm.x930073498.library.BaseItem;
import com.x930073498.item_selector_lib.base.DataChild;
import com.x930073498.item_selector_lib.base.DataGroup;
import com.x930073498.item_selector_lib.base.bridge.ChildItem;
import com.x930073498.item_selector_lib.base.bridge.GroupItem;
import com.x930073498.item_selector_lib.base.bridge.SelectedItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by 930073498 on 2017/7/24.
 */

public class Controller {

    private List<BaseItem> showItems = new ArrayList<>();
    private ArrayMap<GroupItem, List<ChildItem>> deleteItems = new ArrayMap<>();
    private DataPresenter presenter;
    private BaseAdapter mainAdapter;
    private List<SelectedItem> selectedItems = new ArrayList<>();

    public Controller(DataPresenter presenter, BaseAdapter mainAdapter, BaseAdapter selectedAdapter) {
        this.presenter = presenter;
        showItems = new ArrayList<>(presenter.getOriginalItems());
        this.mainAdapter = mainAdapter;
        mainAdapter.setData(showItems);
        selectedAdapter.setData(selectedItems);
    }

    public void showOriginal() {
        showItems = presenter.parseMapToList(presenter.getMap());
        mainAdapter.setData(showItems);
    }

    public void showSearch(DataGroup group, String name) {
        if (group == null && (TextUtils.isEmpty(name) || TextUtils.isEmpty(name.trim()))) {
            showOriginal();
        } else {
            search(group, name);
            mainAdapter.setData(showItems);
        }
    }

    private void search(DataGroup group, String name) {
        if (group == null && (TextUtils.isEmpty(name) || TextUtils.isEmpty(name.trim()))) {
            showItems = presenter.parseMapToList(presenter.getMap());
            return;
        }
        showItems = new ArrayList<>();
        Set<Map.Entry<GroupItem, List<ChildItem>>> set = presenter.getMap().entrySet();
        GroupItem item = null;
        for (Map.Entry<GroupItem, List<ChildItem>> entry : set
                ) {
            if (entry == null) continue;
            GroupItem temp = entry.getKey();
            if (temp == null) continue;
            List<ChildItem> children = entry.getValue();
            if (group == null) {
                for (ChildItem child : children
                        ) {
                    if (child == null) continue;
                    DataChild data = child.getChild();
                    if (data == null) continue;
                    if (TextUtils.isEmpty(data.provideItemName()) || TextUtils.isEmpty(data.provideItemName().toString().trim()))
                        continue;
                    if (data.provideItemName().toString().contains(name)) {
                        if (temp != item) {
                            showItems.add(item);
                            item = temp;
                        }
                        showItems.add(child);
                    }
                }
            } else {
                if (group.equals(temp.getGroup())) {
                    if (TextUtils.isEmpty(name) || TextUtils.isEmpty(name.trim())) {
                        GroupItem groupItem = presenter.getGroupItem(group);
                        showItems.add(groupItem);
                        showItems.addAll(presenter.getChildren(groupItem));
                        return;
                    } else {
                        GroupItem groupItem = presenter.getGroupItem(group);
                        showItems.add(groupItem);
                        List<ChildItem> items = presenter.getChildren(groupItem);
                        if (items == null) continue;
                        for (ChildItem childItem : items
                                ) {
                            if (childItem == null) continue;
                            DataChild child = childItem.getChild();
                            if (child == null) continue;
                            CharSequence childName = child.provideItemName();
                            if (childName == null) continue;
                            String childNameString = childName.toString();
                            if (childNameString.contains(name)) showItems.add(childItem);
                        }
                    }
                }
            }
        }


    }

    public void expand(DataGroup group) {
        GroupItem item = presenter.getGroupItem(group);
        int index = showItems.indexOf(item);
        ArrayList<ChildItem> childItems = new ArrayList<>();
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
    }

    public void collapse(DataGroup group) {
        GroupItem item = presenter.getGroupItem(group);
        int index = showItems.indexOf(item);
        List<ChildItem> children = deleteItems.remove(item);
        showItems.addAll(index + 1, children);
    }

    public void select(DataChild child) {
        ChildItem childItem = presenter.getChildItem(child);
        if (child == null) return;
        childItem.setSelected(false);
        SelectedItem item = getSelectedItem(child);
        if (item == null) {
            SelectedItem selectedItem = new SelectedItem(child);
            selectedItems.add(selectedItem);
        }

    }

    public void unSelect(DataChild child) {
        ChildItem childItem = presenter.getChildItem(child);
        if (child == null) return;
        childItem.setSelected(false);
        SelectedItem item = getSelectedItem(child);
        if (item == null) return;
        deleteItems.remove(item);
    }

    private SelectedItem getSelectedItem(DataChild child) {
        if (child == null) return null;
        if (selectedItems == null) return null;
        for (SelectedItem item : selectedItems
                ) {
            if (item == null) continue;
            if (item.getChild() == null) continue;
            if (item.getChild().equals(child)) return item;
        }
        return null;
    }


}
