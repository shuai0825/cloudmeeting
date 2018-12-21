package com.pmcc.base_module.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;

import com.pmcc.base_module.base.BaseApp;


public class UIUtils {
    public static Context getContext() {
        return BaseApp.getAppContext();
    }



    /**
     * 根据string-array id 获取 数组
     */
    public static String[] getStringArray(int id) {
        return getResource().getStringArray(id);
    }

    public static Resources getResource() {
        return getContext().getResources();
    }

    public static Drawable getDrawable(int id) {
        return getResource().getDrawable(id);
    }

    public static int getColor(int id) {
        return getResource().getColor(id);
    }

    public static int getDimention(int id) {
        return (int) getResource().getDimension(id);
    }

    public static String getString(int id) {
        return getResource().getString(id);
    }
    /**
     * dip转换px
     */
    public static int dip2px(int dip) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /**
     * pxz转换dip
     */

    public static int px2dip(int px) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     *状态栏的高度
     */
    public static int getStatusBarHeight() {
        int resourceId = BaseApp.getAppContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        return BaseApp.getAppContext().getResources().getDimensionPixelSize(resourceId);
    }

    /**
     *屏幕的宽高，高：dm.heightPixels；宽：dm.widthPixels
     */
    public static DisplayMetrics getScreenSize() {
        return getResource().getDisplayMetrics();
    }


}