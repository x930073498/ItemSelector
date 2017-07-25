package com.x930073498.item_selector_lib.base;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatButton;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.x930073498.item_selector_lib.base.view.CenterAlignImageSpan;

/**
 * Created by 930073498 on 2017/7/24.
 */

public class DataBindingAdapter {
    @BindingAdapter(value = {"drawableLeft", "text", "drawableWidth", "drawableHeight", "drawablePadding"}, requireAll = false)
    public static void setTextLeftDrawableAndText(TextView view, Drawable drawable, CharSequence text, float drawableWidth, float drawableHeight, int drawablePadding) {
        if (drawable == null) {
            view.setText(text);
        } else {
//            drawable.setBounds(0, 0, (float) size, (float) size);
            float width = drawableWidth;
            float height = drawableHeight;
//            float width = dip2px(view.getContext(), drawableWidth);
//            float height = dip2px(view.getContext(), drawableHeight);
            drawable.setBounds(0, 0, (int) width, (int) height);
            CenterAlignImageSpan span = new CenterAlignImageSpan(drawable, CenterAlignImageSpan.ALIGN_FONTCENTER);
            Log.d("xj", "setTextLeftDrawableAndText: " + span);
            StringBuilder stringBuilder = new StringBuilder(" ");
            drawablePadding = Math.max(0, drawablePadding);
            for (int i = 0; i < drawablePadding; i++) {
                stringBuilder.append(" ");
            }
            SpannableStringBuilder builder = new SpannableStringBuilder(stringBuilder);
            if (text != null)
                builder.append(text);
            builder.setSpan(span, 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            Log.d("xj", "setTextLeftDrawableAndText: " + builder);
            view.setText(builder);
        }
    }


    @BindingAdapter(value = {"drawableLeft", "text", "size","drawablePadding"}, requireAll = false)
    public static void setTextLeftDrawableAndText(TextView view, Drawable drawable, CharSequence text, float size,int drawablePadding) {
        setTextLeftDrawableAndText(view, drawable, text, size, size,drawablePadding);

    }


    @BindingAdapter(value = {"drawableRight", "text", "drawableWidth", "drawableHeight","drawablePadding"}, requireAll = false)
    public static void setTextRightDrawableAndText(TextView view, Drawable drawable, CharSequence text, float drawableWidth, float drawableHeight,int drawablePadding) {
        if (drawable == null) {
            view.setText(text);
        } else {
//            drawable.setBounds(0, 0, (float) size, (float) size);
            float width = drawableWidth;
            float height = drawableHeight;
//            float width = dip2px(view.getContext(), drawableWidth);
//            float height = dip2px(view.getContext(), drawableHeight);
            drawable.setBounds(0, 0, (int) width, (int) height);
            CenterAlignImageSpan span = new CenterAlignImageSpan(drawable, CenterAlignImageSpan.ALIGN_FONTCENTER);
            StringBuilder stringBuilder=new StringBuilder(" ");
            drawablePadding=Math.max(0,drawablePadding);
            for (int i = 0; i <drawablePadding ; i++) {
                stringBuilder.append(" ");
            }
            SpannableStringBuilder builder = new SpannableStringBuilder(text == null ? "" : text);
            builder.append(stringBuilder);
            int length = builder.length();
            if (length == 1) {
                builder.setSpan(span, 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            } else {
                builder.setSpan(span, length - 1, length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            }
            view.setText(builder);

        }
    }


    @BindingAdapter(value = {"drawableRight", "text", "size","drawablePadding"}, requireAll = false)
    public static void setTextRightDrawableAndText(TextView view, Drawable drawable, CharSequence text, float size,int drawablePadding) {
        setTextRightDrawableAndText(view, drawable, text, size, size,drawablePadding);

    }



    private static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    private static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
