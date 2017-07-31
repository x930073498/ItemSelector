package com.x930073498.item_selector_lib.base;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;

import com.x930073498.item_selector_lib.R;

/**
 * Created by Administrator on 2017/7/31 0031.
 */

public class Theme {

    private Context context;

    public Theme(Context context) {
        this.context = context;
        mainBackground = getDrawable(R.color.color_background_gray);
        titleBarBackground = getDrawable(R.color.color_background_blue);
        iconBackDrawable = getDrawable(R.drawable.icon_return_back);
        textBack = "返回";
        textBackTextColor = getColor(R.color.text_color_white);
        titleTextColor = textBackTextColor;
        textSettingTextColor = titleTextColor;
        textSetting = "查看";
        iconDropDown = getDrawable(R.drawable.icon_drop_down);
        textGroupSelectedTextColor = getColor(R.color.text_color_black);
        iconFlagSearchText = getDrawable(R.drawable.icon_search_flag_holder);
        hintTextSearch = "请输入人名进行检索...";
        iconSearchButton = getDrawable(R.drawable.icon_search);
        textSearchButton = "搜索";
        searchButtonBackground = getDrawable(R.drawable.selector_button);
        textSearchButtonTextColor = getColor(R.color.text_color_white);
        submitButtonBackground = getDrawable(R.drawable.selector_button_no_corner);
        textSubmitButtonText = "提交";
        textSubmitButtonTextColor = getColor(R.color.text_color_white);
        iconGroupDropDown = getDrawable(R.drawable.icon_arrow_down_blue_circle);
    }

    private Drawable mainBackground;
    private Drawable titleBarBackground;
    private Drawable iconBackDrawable;
    private CharSequence textBack;
    private
    @ColorInt
    int textBackTextColor;
    private
    @ColorInt
    int titleTextColor;
    private
    @ColorInt
    int textSettingTextColor;
    private CharSequence textSetting;
    private Drawable iconDropDown;
    private
    @ColorInt
    int textGroupSelectedTextColor;
    private Drawable iconFlagSearchText;
    private CharSequence hintTextSearch;
    private Drawable iconSearchButton;
    private CharSequence textSearchButton;
    private Drawable searchButtonBackground;
    private
    @ColorInt
    int textSearchButtonTextColor;
    private Drawable submitButtonBackground;
    private CharSequence textSubmitButtonText;
    private
    @ColorInt
    int textSubmitButtonTextColor;

    private Drawable iconGroupDropDown;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Drawable getIconGroupDropDown() {
        return iconGroupDropDown;
    }

    public void setIconGroupDropDown(Drawable iconGroupDropDown) {
        this.iconGroupDropDown = iconGroupDropDown;
    }

    public Drawable getMainBackground() {
        return mainBackground;
    }

    public void setMainBackground(Drawable mainBackground) {
        this.mainBackground = mainBackground;
    }

    public Drawable getTitleBarBackground() {
        return titleBarBackground;
    }

    public void setTitleBarBackground(Drawable titleBarBackground) {
        this.titleBarBackground = titleBarBackground;
    }

    public Drawable getIconBackDrawable() {
        return iconBackDrawable;
    }

    public void setIconBackDrawable(Drawable iconBackDrawable) {
        this.iconBackDrawable = iconBackDrawable;
    }

    public CharSequence getTextBack() {
        return textBack;
    }

    public void setTextBack(CharSequence textBack) {
        this.textBack = textBack;
    }

    public int getTextBackTextColor() {
        return textBackTextColor;
    }

    public void setTextBackTextColor(int textBackTextColor) {
        this.textBackTextColor = textBackTextColor;
    }

    public int getTitleTextColor() {
        return titleTextColor;
    }

    public void setTitleTextColor(int titleTextColor) {
        this.titleTextColor = titleTextColor;
    }

    public int getTextSettingTextColor() {
        return textSettingTextColor;
    }

    public void setTextSettingTextColor(int textSettingTextColor) {
        this.textSettingTextColor = textSettingTextColor;
    }

    public CharSequence getTextSetting() {
        return textSetting;
    }

    public void setTextSetting(CharSequence textSetting) {
        this.textSetting = textSetting;
    }

    public Drawable getIconDropDown() {
        return iconDropDown;
    }

    public void setIconDropDown(Drawable iconDropDown) {
        this.iconDropDown = iconDropDown;
    }

    public int getTextGroupSelectedTextColor() {
        return textGroupSelectedTextColor;
    }

    public void setTextGroupSelectedTextColor(int textGroupSelectedTextColor) {
        this.textGroupSelectedTextColor = textGroupSelectedTextColor;
    }

    public Drawable getIconFlagSearchText() {
        return iconFlagSearchText;
    }

    public void setIconFlagSearchText(Drawable iconFlagSearchText) {
        this.iconFlagSearchText = iconFlagSearchText;
    }

    public CharSequence getHintTextSearch() {
        return hintTextSearch;
    }

    public void setHintTextSearch(CharSequence hintTextSearch) {
        this.hintTextSearch = hintTextSearch;
    }

    public Drawable getIconSearchButton() {
        return iconSearchButton;
    }

    public void setIconSearchButton(Drawable iconSearchButton) {
        this.iconSearchButton = iconSearchButton;
    }

    public CharSequence getTextSearchButton() {
        return textSearchButton;
    }

    public void setTextSearchButton(CharSequence textSearchButton) {
        this.textSearchButton = textSearchButton;
    }

    public Drawable getSearchButtonBackground() {
        return searchButtonBackground;
    }

    public void setSearchButtonBackground(Drawable searchButtonBackground) {
        this.searchButtonBackground = searchButtonBackground;
    }

    public int getTextSearchButtonTextColor() {
        return textSearchButtonTextColor;
    }

    public void setTextSearchButtonTextColor(int textSearchButtonTextColor) {
        this.textSearchButtonTextColor = textSearchButtonTextColor;
    }

    public Drawable getSubmitButtonBackground() {
        return submitButtonBackground;
    }

    public void setSubmitButtonBackground(Drawable submitButtonBackground) {
        this.submitButtonBackground = submitButtonBackground;
    }

    public CharSequence getTextSubmitButtonText() {
        return textSubmitButtonText;
    }

    public void setTextSubmitButtonText(CharSequence textSubmitButtonText) {
        this.textSubmitButtonText = textSubmitButtonText;
    }

    public int getTextSubmitButtonTextColor() {
        return textSubmitButtonTextColor;
    }

    public void setTextSubmitButtonTextColor(int textSubmitButtonTextColor) {
        this.textSubmitButtonTextColor = textSubmitButtonTextColor;
    }

    private Drawable getDrawable(int resId) {
        return ContextCompat.getDrawable(context, resId);
    }

    private int getColor(int colorRes) {
        return ContextCompat.getColor(context, colorRes);
    }


    @Override
    public String toString() {
        return "Theme{" +
                "context=" + context +
                ", mainBackground=" + mainBackground +
                ", titleBarBackground=" + titleBarBackground +
                ", iconBackDrawable=" + iconBackDrawable +
                ", textBack=" + textBack +
                ", textBackTextColor=" + textBackTextColor +
                ", titleTextColor=" + titleTextColor +
                ", textSettingTextColor=" + textSettingTextColor +
                ", textSetting=" + textSetting +
                ", iconDropDown=" + iconDropDown +
                ", textGroupSelectedTextColor=" + textGroupSelectedTextColor +
                ", iconFlagSearchText=" + iconFlagSearchText +
                ", hintTextSearch=" + hintTextSearch +
                ", iconSearchButton=" + iconSearchButton +
                ", textSearchButton=" + textSearchButton +
                ", searchButtonBackground=" + searchButtonBackground +
                ", textSearchButtonTextColor=" + textSearchButtonTextColor +
                ", submitButtonBackground=" + submitButtonBackground +
                ", textSubmitButtonText=" + textSubmitButtonText +
                ", textSubmitButtonTextColor=" + textSubmitButtonTextColor +
                '}';
    }
}
