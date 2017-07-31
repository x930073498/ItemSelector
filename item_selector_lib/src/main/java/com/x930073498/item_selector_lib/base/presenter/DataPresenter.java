package com.x930073498.item_selector_lib.base.presenter;

import android.content.Context;
import android.graphics.drawable.Drawable;
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

public class DataPresenter<CHILD extends DataChild,GROUP extends DataGroup<CHILD>> {
    private List<CHILD> children = new ArrayList<>();
    private List<GROUP> groups = new ArrayList<>();
    private List<BaseItem> originalItems = new ArrayList<>();
    private List<GroupItem<CHILD,GROUP>> groupItems = new ArrayList<>();
    private ArrayMap<GROUP, GroupItem<CHILD,GROUP>> groupDataMap = new ArrayMap<>();
    private ArrayMap<CHILD, ChildItem<CHILD>> childDataMap = new ArrayMap<>();
    private ArrayMap<GroupItem<CHILD,GROUP>, List<ChildItem<CHILD>>> map = new ArrayMap<>();
    private Context context;
    private Drawable iconDropDown;


    private void parseData(List<GROUP> groups) {
        if (context == null) return;
        if (groups == null) return;
        List<CHILD> items;
        GroupItem<CHILD,GROUP> groupItem;
        List<ChildItem<CHILD>> childItems;
        ChildItem<CHILD> childItem;
        for (GROUP group : groups
                ) {
            if (group == null) continue;
            items = group.provideChildren();
            if (items == null) continue;
            children.addAll(group.provideChildren());
            groupItem = new GroupItem<>(context, group,iconDropDown);
            groupItems.add(groupItem);
            groupDataMap.put(group, groupItem);
            originalItems.add(groupItem);
            childItems = new ArrayList<>();
            for (CHILD childData : items
                    ) {
                if (childData == null) continue;
                childItem = new ChildItem<>(context, childData);
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

    public DataPresenter(Context context, List<CHILD> children,Drawable iconDropDown) {
        this.children = children;
        this.context = context;
        this.iconDropDown=iconDropDown;
        parseData();
    }

    public DataPresenter(List<GROUP> groups, Context context,Drawable iconDropDown) {
        this.context = context;
        this.groups = groups;
        this.iconDropDown=iconDropDown;
        parseData(groups);
    }


    public List<GROUP> getGroups() {
        return groups;
    }

    List<BaseItem> getOriginalItems() {
        return originalItems;
    }

    //
    private void parseData() {
        if (children == null) return;
        GroupItem<CHILD,GROUP> item = null;
        List<ChildItem<CHILD>> temp = new ArrayList<>();
        ChildItem<CHILD> childItem;
        GroupItem<CHILD,GROUP> groupItem;
        GROUP group;
        for (CHILD child : children
                ) {
            if (child == null) continue;
            childItem = new ChildItem<>(context, child);
            group = child.provideGroup();
            groupItem = new GroupItem<>(context, group,iconDropDown);
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


    ChildItem<CHILD> getChildItem(CHILD child) {
        return childDataMap.get(child);
    }


    List<ChildItem<CHILD>> getChildren(GroupItem<CHILD,GROUP> groupItem) {

        return map.get(groupItem);
    }

    GroupItem<CHILD,GROUP> getGroupItem(GROUP group) {
        return groupDataMap.get(group);
    }


    void resetGroupExpand() {
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
