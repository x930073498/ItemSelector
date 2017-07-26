package com.x930073498.item_selector_lib.base.presenter;

import android.content.Context;
import android.support.v4.util.ArrayMap;

import com.mvvm.x930073498.library.BaseItem;
import com.x930073498.item_selector_lib.base.DataChild;
import com.x930073498.item_selector_lib.base.DataGroup;
import com.x930073498.item_selector_lib.base.bridge.ChildItem;
import com.x930073498.item_selector_lib.base.bridge.GroupItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 930073498 on 2017/7/24.
 */

public class DataPresenter {
    private List<DataChild> children=new ArrayList<>();
    private List<DataGroup> groups = new ArrayList<>();
    private List<BaseItem> originalItems = new ArrayList<>();
    private List<GroupItem> groupItems = new ArrayList<>();
    private ArrayMap<DataGroup, GroupItem> groupDataMap = new ArrayMap<>();
    private ArrayMap<DataChild, ChildItem> childDataMap = new ArrayMap<>();
    private ArrayMap<GroupItem, List<ChildItem>> map = new ArrayMap<>();
    private Context context;


    private void parseData(List<DataGroup> groups) {
        if (context == null) return;
        if (groups == null) return;
        for (DataGroup group : groups
                ) {
            if (group == null) continue;
            List<DataChild> items = group.provideChildren();
            if (items == null) continue;
            children.addAll(group.provideChildren());
            GroupItem groupItem = new GroupItem(context, group);
            groupItems.add(groupItem);
            groupDataMap.put(group, groupItem);
            originalItems.add(groupItem);
            List<ChildItem> childItems = new ArrayList<>();
            for (DataChild childData : items
                    ) {
                if (childData == null) continue;
                ChildItem childItem = new ChildItem(context, childData);
                childItems.add(childItem);
                childDataMap.put(childData, childItem);
            }
            originalItems.addAll(childItems);
            map.put(groupItem, childItems);
        }
    }


    public Context getContext() {
        return context;
    }

    public DataPresenter(Context context, List<DataChild> children) {
        this.children = children;
        this.context = context;
        parseData();
    }

    public DataPresenter(List<DataGroup> groups, Context context) {
        this.context = context;
        this.groups = groups;
        parseData(groups);
    }


    public List<DataGroup> getGroups() {
        return groups;
    }

    public List<BaseItem> getOriginalItems() {
        return originalItems;
    }

//
    private void parseData() {
        if (children == null) return;
        GroupItem item = null;
        List<ChildItem> temp = new ArrayList<>();
        for (DataChild child : children
                ) {
            if (child == null) continue;
            ChildItem childItem = new ChildItem(context, child);
            DataGroup group = child.provideGroup();
            GroupItem groupItem = new GroupItem(context, group);
            if (groupItem.equals(item)) {
                temp.add(childItem);
            } else {
                groups.add(group);
                groupItems.add(groupItem);
                groupDataMap.put(group, groupItem);
                originalItems.add(groupItem);
                if (item == null) {
                    item = groupItem;
                    temp.add(childItem);
                } else {
                    map.put(item, temp);
                    temp = new ArrayList<>();
                    temp.add(childItem);
                    item = groupItem;
                }
            }
            originalItems.add(childItem);
            childDataMap.put(child, childItem);
        }
        if (item != null)
            map.put(item, temp);
    }


//    private void parseData() {
//        if (children == null || children.size() <= 0) return;
//        ChildGroupData group = null;
//        List<ChildItem> items = null;
//        for (DataChild child : children
//                ) {
//            if (child == null) continue;
//            if (!child.provideGroup().equals(group)) {
//                group = child.provideGroup();
//                GroupItem item = new GroupItem(context, group);
//                groupDataMap.put(group, item);
//                if (items != null) {
//                    map.put(item, items);
//                }
//                items = new ArrayList<>();
//                groups.add(group);
//                originalItems.add(item);
//            }
//            ChildItem item = new ChildItem(context, child);
//            childDataMap.put(child, item);
//            items.add(item);
//            originalItems.add(item);
//        }
//        if (group != null) {
//            GroupItem item = new GroupItem(context, group);
//            map.put(item, items);
//        }
//
//    }

    public ChildItem getChildItem(DataChild child) {
        return childDataMap.get(child);
    }


    public List<ChildItem> getChildren(GroupItem groupItem) {

        return map.get(groupItem);
    }

    public GroupItem getGroupItem(DataGroup group) {
        return groupDataMap.get(group);
    }


    public void resetGroupExpand() {
        if (groupItems == null) return;
        for (GroupItem item : groupItems
                ) {
            if (item == null) continue;
            item.setExpand(true);
        }
    }

    public void onDestroy() {
        if (children != null) {
            children.clear();
            children = null;
        }
        if (groups != null) {
            groups.clear();
            groups = null;
        }
        if (originalItems != null) {
            originalItems.clear();
            originalItems = null;
        }
        if (groupItems != null) {
            groupItems.clear();
            groupItems = null;
        }
        if (groupDataMap != null) {
            groupDataMap.clear();
            groupDataMap = null;
        }
        if (childDataMap != null) {
            childDataMap.clear();
            childDataMap = null;
        }
        if (map != null) {
            map.clear();
            map = null;
        }
    }

}
