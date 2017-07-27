package com.x930073498.itemselector;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.x930073498.item_selector_lib.base.DataChild;
import com.x930073498.item_selector_lib.base.DataGroup;
import com.x930073498.item_selector_lib.base.ItemSelectorActivity;
import com.x930073498.item_selector_lib.base.OnCompletedListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnCompletedListener {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        DataBindingUtil.setContentView(R.la)
        DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    public void onClick(View view) {
        ItemSelectorActivity.openActivity(this, this, createSecondData(), "人员选择", 0, 3);
//        ItemSelectorActivity.openActivity(this, createData(), this, "人员选择", 0, 6);
    }


    private static final String[] groupNames = new String[]{"派出所", "安监", "城管", "社区", "工作站"};
    private static final String[] childNames = new String[]{"施小敏", "李伟龙", "戴科", "郑希", "王旭阳", "谭志星", "贾玉娟", "施先锋"};

    private static List<DataChild> createData() {
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

    private static List<DataGroup> createSecondData() {
        List<DataGroup> groups = new ArrayList<>();
        for (int i = 0; i < groupNames.length; i++) {
            List<DataChild> list = new ArrayList<>();
            for (int j = 0; j < childNames.length; j++) {
                SecondChild childData = new SecondChild(childNames[j].concat(String.valueOf(i)).concat(String.valueOf(j)));
                if (childNames[j].contains("贾")) childData.setMale(false);
                list.add(childData);
            }
            SecondGroup data = new SecondGroup(groupNames[i], list);
            groups.add(data);
        }
        return groups;
    }

    @Override
    public void completed(List children) {

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

        @Override
        public DataGroup provideGroup() {
            return group;
        }

        @Override
        public CharSequence provideName() {
            return name;
        }

        @Override
        public CharSequence provideDescription() {
            return "1518476547";
        }

        @NonNull
        @Override
        public CharSequence provideId() {
            return String.valueOf(id++);
        }

        @Override
        public Drawable provideNameIcon(Context context) {
            return ContextCompat.getDrawable(context, R.drawable.icon_male);
        }

        @Override
        public Drawable provideDescriptionIcon(Context context) {
            return ContextCompat.getDrawable(context, R.drawable.icon_tel);
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

        @Override
        public List<DataChild> provideChildren() {
            return null;
        }

        @NonNull
        @Override
        public CharSequence provideId() {
            return String.valueOf(id++);
        }

        @Override
        public CharSequence provideName() {
            return name;
        }

        @Override
        public Drawable provideIcon(Context context) {
            return ActivityCompat.getDrawable(context, R.drawable.icon_group);
        }
    }


    private static class SecondGroup extends MyGroupData {
        private List<DataChild> children;

        public SecondGroup(String name, List<DataChild> children) {
            super(name);
            this.children = children;
        }

        @Override
        public List<DataChild> provideChildren() {
            return children;
        }
    }

    private static class SecondChild extends MyChildData {


        public boolean isMale() {
            return isMale;
        }

        public void setMale(boolean male) {
            isMale = male;
        }

        private boolean isMale = true;

        @Override
        public Drawable provideNameIcon(Context context) {
            return isMale ? super.provideNameIcon(context) : ActivityCompat.getDrawable(context, R.drawable.icon_female);
        }

        public SecondChild(String name) {
            super(null, name);
        }

    }

}
