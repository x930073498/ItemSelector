package com.x930073498.itemselector;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.x930073498.item_selector_lib.base.ItemSelectorActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        DataBindingUtil.setContentView(R.la)
        DataBindingUtil.setContentView(this,R.layout.activity_main);
    }
    public void onClick(View view){
        startActivity(new Intent(this, ItemSelectorActivity.class));
    }
}
