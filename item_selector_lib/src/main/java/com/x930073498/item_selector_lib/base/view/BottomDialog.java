package com.x930073498.item_selector_lib.base.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.x930073498.item_selector_lib.R;


/**
 * Created by Administrator on 2017/7/28 0028.
 */

public class BottomDialog extends Dialog {

    public BottomDialog(Context context, View view) {
        super(context, R.style.ThemeTransparent);
        setContentView(view);
        setCanceledOnTouchOutside(true);
        if (getWindow() != null) {
            WindowManager.LayoutParams a = getWindow().getAttributes();
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            a.width = getWindow().getWindowManager().getDefaultDisplay().getWidth() ;
            a.height = WindowManager.LayoutParams.WRAP_CONTENT;
            a.dimAmount = 0.6f; // 背景遮盖，0.0f表示无阴影
            getWindow().setGravity(Gravity.BOTTOM);//getWindow()代表整个窗体
            getWindow().setAttributes(a);
        }
    }
}
