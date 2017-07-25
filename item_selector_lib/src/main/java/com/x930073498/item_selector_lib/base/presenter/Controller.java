package com.x930073498.item_selector_lib.base.presenter;

import android.databinding.ObservableArrayList;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Log;

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

import static android.content.ContentValues.TAG;

/**
 * Created by 930073498 on 2017/7/24.
 */

public class Controller {

    private List<BaseItem> showItems = new ObservableArrayList<>();
    private ArrayMap<GroupItem, List<ChildItem>> deleteItems = new ArrayMap<>();
    private DataPresenter presenter;
    private BaseAdapter mainAdapter;
    private List<SelectedItem> selectedItems = new ObservableArrayList<>();

    public Controller(DataPresenter presenter, BaseAdapter mainAdapter, BaseAdapter selectedAdapter) {
        this.presenter = presenter;
        showItems.addAll(presenter.getOriginalItems());
        this.mainAdapter = mainAdapter;
        mainAdapter.setData(showItems);
        selectedAdapter.setData(selectedItems);
    }

    public void showOriginal() {
        showItems=new ObservableArrayList<>();
        showItems.addAll(presenter.getOriginalItems());
    }

    public void showSearch(DataGroup group, String name) {
        if (group == null && (TextUtils.isEmpty(name) || TextUtils.isEmpty(name.trim()))) {
            showOriginal();
        } else {
            search(group, name);
            mainAdapter.setData(showItems);
        }
    }

    private List<BaseItem> searchGroup(DataGroup group) {
        List<BaseItem> original = presenter.getOriginalItems();
        if (group == null) return original;
        List<BaseItem> list = new ArrayList<>();
        GroupItem item = presenter.getGroupItem(group);
        if (item == null) return list;
        list.add(item);

        if (group.provideGroupName().equals("派出所")){


            Log.d(TAG, "searchGroup: 派出所="+presenter.getChildren(item));
        }
        list.addAll(presenter.getChildren(item));
        return list;
    }

    private List<BaseItem> search(List<BaseItem> original, String name) {
        Log.d(TAG, "search: name="+name);
        if (original == null) return new ArrayList<>();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(name.trim())) return original;
        GroupItem item = null;
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
                    ChildItem childItem = (ChildItem) baseItem;
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


    private void search(DataGroup group, String name) {
        List<BaseItem> list=search(searchGroup(group),name);
        showItems=new ObservableArrayList<>();
        showItems.addAll(list);


//        if (group == null && (TextUtils.isEmpty(name) || TextUtils.isEmpty(name.trim()))) {
//            showItems.clear();
//            showItems.addAll(presenter.getOriginalItems());
//            return;
//        }
//        showItems.clear();
//        Set<Map.Entry<GroupItem, List<ChildItem>>> set = presenter.getMap().entrySet();
//        GroupItem item = null;
//        for (Map.Entry<GroupItem, List<ChildItem>> entry : set
//                ) {
//            if (entry == null) continue;
//            GroupItem temp = entry.getKey();
//            if (temp == null) continue;
//            List<ChildItem> children = entry.getValue();
//            Log.d(TAG, "search: group=" + group);
//            if (group == null) {
//                for (ChildItem child : children
//                        ) {
//                    if (child == null) continue;
//                    DataChild data = child.getChild();
//                    if (data == null) continue;
//                    if (TextUtils.isEmpty(data.provideItemName()) || TextUtils.isEmpty(data.provideItemName().toString().trim()))
//                        continue;
//                    if (data.provideItemName().toString().contains(name)) {
//                        if (temp != item) {
//                            showItems.add(item);
//                            item = temp;
//                        }
//                        showItems.add(child);
//                    }
//                }
//            } else {
//                if (group.equals(temp.getGroup())) {
//                    if (TextUtils.isEmpty(name) || TextUtils.isEmpty(name.trim())) {
//                        GroupItem groupItem = presenter.getGroupItem(group);
//                        showItems.add(groupItem);
//                        List<ChildItem> list = presenter.getChildren(groupItem);
//                        if (list == null) {
//                            showItems.clear();
//                            return;
//                        }
//                        showItems.addAll(list);
//                        return;
//                    } else {
//                        GroupItem groupItem = presenter.getGroupItem(group);
//                        showItems.add(groupItem);
//                        List<ChildItem> items = presenter.getChildren(groupItem);
//                        if (items == null) continue;
//                        for (ChildItem childItem : items
//                                ) {
//                            if (childItem == null) continue;
//                            DataChild child = childItem.getChild();
//                            if (child == null) continue;
//                            CharSequence childName = child.provideItemName();
//                            if (childName == null) continue;
//                            String childNameString = childName.toString();
//                            if (childNameString.contains(name)) showItems.add(childItem);
//                        }
//                    }
//                }
//            }
//        }
//        Log.d(TAG, "search: showItems=" + showItems);
//
//        mainAdapter.setData(showItems);

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
        if (childItem != null) {
            childItem.setSelected(true);
        }
        SelectedItem item = getSelectedItem(child);
        if (item == null) {
            Log.d(TAG, "select: item=null");
            SelectedItem selectedItem = new SelectedItem(child);
            selectedItems.add(selectedItem);
        } else {
            selectedItems.add(item);
        }

    }

    public void unSelect(DataChild child) {
        ChildItem childItem = presenter.getChildItem(child);
        if (child == null) return;
        childItem.setSelected(false);
        SelectedItem item = getSelectedItem(child);
        if (item == null) return;
        selectedItems.remove(item);
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
