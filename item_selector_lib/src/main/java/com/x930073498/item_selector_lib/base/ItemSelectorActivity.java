package com.x930073498.item_selector_lib.base;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

import com.x930073498.item_selector_lib.R;
import com.x930073498.item_selector_lib.base.bridge.ActivityViewModel;
import com.x930073498.item_selector_lib.base.presenter.DataPresenter;
import com.x930073498.item_selector_lib.databinding.ActivitySelectorItemBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 930073498 on 2017/7/24.
 */

public class ItemSelectorActivity extends AppCompatActivity {
    public final static String TAG = "ItemSelectorActivity";

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


    public static void openTest(Context context) {
        openActivity(context, createData(), null, "选择", 0, 3);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        parseIntent(getIntent());
        super.onCreate(savedInstanceState);
        ActivitySelectorItemBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_selector_item);
        binding.setData(new ActivityViewModel(this, new DataPresenter(this, children), title, max, min, listener));
    }

    private void parseIntent(Intent intent) {
        if (intent == null) return;
        int key = intent.getIntExtra(KEY, -1);
        if (map != null)
            children = map.get(key);
        if (listeners != null) {
            listener = listeners.get(key);
        }
        Log.d(TAG, "parseIntent: " + children);
        min = intent.getIntExtra(KEY_MIN, MIN);
        max = intent.getIntExtra(KEY_MAX, MAX);
        title = intent.getCharSequenceExtra(KEY_TITLE);
    }


    private static List<DataChild> createData() {
        String[] groupNames = new String[]{"派出所", "安监", "城管","社区","工作站"};
        String[] childNames = new String[]{"施小敏", "李伟龙", "戴科", "郑希", "王旭阳","谭志星","贾玉娟","施先锋"};
        List<DataChild> children = new ArrayList<>();
        for (int i = 0; i < groupNames.length; i++) {
            MyGroupData data = new MyGroupData(groupNames[i]);
            for (int j = 0; j < childNames.length; j++) {
                MyChildData childData = new MyChildData(data, childNames[j].concat(String.valueOf(i)).concat(String.valueOf(j)));
                children.add(childData);
            }
        }
        return children;
    }

    public static class MyChildData implements DataChild {
        MyGroupData group;
        String name;

        @Override
        public String toString() {
            return "MyChildData{" +
                    "group=" + group +
                    ", name='" + name + '\'' +
                    '}';
        }

        public MyChildData(MyGroupData group, String name) {
            this.group = group;
            this.name = name;
        }

        @NonNull
        @Override
        public DataGroup provideGroup() {
            return group;
        }

        @Override
        public CharSequence provideItemName() {
            return name;
        }

        @Override
        public CharSequence provideItemDescription() {
            return "1518476547";
        }

        @NonNull
        @Override
        public CharSequence provideItemId() {
            return String.valueOf(id++);
        }

        @Override
        public Drawable provideItemNameIcon(Context context) {
            return ContextCompat.getDrawable(context, R.drawable.nan);
        }

        @Override
        public Drawable provideItemDescriptionIcon(Context context) {
            return ContextCompat.getDrawable(context, R.drawable.dianhua);
        }
    }

    private static int id = 0;

    public static class MyGroupData implements DataGroup {
        @Override
        public String toString() {
            return "MyGroupData{" +
                    "name='" + name + '\'' +
                    '}';
        }

        private String name;

        public MyGroupData(String name) {
            this.name = name;
        }

        @NonNull
        @Override
        public CharSequence provideGroupId() {
            return String.valueOf(id++);
        }

        @Override
        public CharSequence provideGroupName() {
            return name;
        }

        @Override
        public Drawable provideGroupIcon(Context context) {
            return ActivityCompat.getDrawable(context, R.drawable.anjian);
        }
    }

}
