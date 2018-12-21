package com.pmcc.base_module.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;

import com.pmcc.base_module.base.BaseApp;


/**
 * Created by ${zhangshuai} on 2018/5/21.
 * 2751157603@qq.com
 * 获取手机及应用信息
 */
public class DeviceUtil {
    /**
     * 获取品牌、型号、唯一序列号
     * @param context
     * @return
     */
    public static String getDeviceInfo(Context context) {
        try {
            return android.os.Build.BRAND + "-" + Build.MODEL +
                    "-" + Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

        } catch (Exception e) {
            return "无法获取设备信息";
        }
    }

    /**
     * 获得当前版本号
     *
     * @return
     */
    public static int getVersionCode() {
        int versionCode = 0;
        try {
            versionCode = BaseApp.getAppContext().getPackageManager().
                    getPackageInfo(BaseApp.getAppContext().getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获得当前版本名称
     *
     * @return
     */
    public static String getVersionName() {
        String versionName = "";
        try {
            versionName = BaseApp.getAppContext().getPackageManager().
                    getPackageInfo(BaseApp.getAppContext().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 通过版本名字比较是否更新
     *
     * @param newVersion
     * @return
     */
    public static boolean checkNameUpdate(String newVersion) {
        try {
            String[] fastVersion = newVersion.split(".");
            String[] localVersion = getVersionName().split(".");
            for (int i = 0; i < localVersion.length; i++) {
                if (Integer.parseInt(fastVersion[i]) > Integer.parseInt(localVersion[i])) {
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}