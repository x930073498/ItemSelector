package com.x930073498.item_selector_lib.base.presenter;

import android.content.Context;
import android.support.v4.util.ArrayMap;

import com.mvvm.x930073498.library.BaseItem;
import com.x930073498.item_selector_lib.base.DataChild;
import com.x930073498.item_selector_lib.base.DataGroup;
import com.x930073498.item_selector_lib.base.bridge.ChildItem;
import com.x930073498.item_selector_lib.base.bridge.GroupItem;
import com.x930073498.item_selector_lib.base.bridge.SelectedItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 930073498 on 2017/7/24.
 */

public class DataPresenter {
    private List<DataChild> children;
    private List<DataGroup> groups = new ArrayList<>();
    private List<BaseItem> originalItems = new ArrayList<>();
    private List<ChildItem> childItems = new ArrayList<>();
    private List<GroupItem> groupItems = new ArrayList<>();
    private ArrayMap<DataGroup, GroupItem> groupDataMap = new ArrayMap<>();
    private ArrayMap<DataChild, ChildItem> childDataMap = new ArrayMap<>();
    private ArrayMap<GroupItem, List<ChildItem>> map = new ArrayMap<>();
    private Context context;

    public DataPresenter(Context context, List<DataChild> children) {
        this.children = children;
        this.context = context;
        parseData();
    }

    public List<DataChild> getChildren() {
        return children;
    }

    public List<DataGroup> getGroups() {
        return groups;
    }

    public List<BaseItem> getOriginalItems() {
        return originalItems;
    }

    public List<ChildItem> getChildItems() {
        return childItems;
    }

    public List<GroupItem> getGroupItems() {
        return groupItems;
    }

    public List<BaseItem> parseMapToList(ArrayMap<GroupItem, List<ChildItem>> map) {
        if (map == null) return null;
        List<BaseItem> items = new ArrayList<>();
        for (GroupItem item : groupItems) {
            if (item == null) continue;
            List<ChildItem> list = map.get(item);
            item.setExpand(true);
            if (list == null) continue;
            items.add(item);
            items.addAll(list);
        }
        return items;
    }

    public ArrayMap<GroupItem, List<ChildItem>> getMap() {
        return map;
    }

    private void parseData() {
        if (children == null || children.size() <= 0) return;
        DataGroup group = null;
        List<ChildItem> items = null;
        for (DataChild child : children
                ) {
            if (child == null) continue;
            if (!child.provideGroup().equals(group)) {
                group = child.provideGroup();
                GroupItem item = new GroupItem(context, group);
                groupDataMap.put(group, item);
                map.put(item, items);
                if (items != null) {
                    map.put(item, items);
                }
                items = new ArrayList<>();
                groups.add(group);
                originalItems.add(item);
                groupItems.add(item);
            }
            ChildItem item = new ChildItem(context, child);
            childDataMap.put(child, item);
            items.add(item);
            originalItems.add(item);
            childItems.add(item);
        }
        if (group != null) {
            GroupItem item = new GroupItem(context, group);
            map.put(item, items);
        }

    }

    public ChildItem getChildItem(DataChild child) {
        return childDataMap.get(child);
    }

    public List<ChildItem> getChildren(GroupItem groupItem) {
        return map.get(groupItem);
    }

    public GroupItem getGroupItem(DataGroup group) {
        return groupDataMap.get(group);
    }

    public ChildItem parseToChild(SelectedItem item) {
        if (item == null) return null;
        DataChild child = item.getChild();
        return getChildItem(child);
    }

}
