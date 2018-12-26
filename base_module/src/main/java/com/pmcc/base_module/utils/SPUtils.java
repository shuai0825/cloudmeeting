package com.pmcc.base_module.utils;

import android.app.Activity;
import android.content.SharedPreferences;

import com.pmcc.base_module.base.BaseApp;

import java.util.Map;

/**
 * Created by ${zhangshuai} on 2018/5/8.
 * 2751157603@qq.com
 */
public class SPUtils {

    private SharedPreferences sp;
   // private LoginBean loginInfo;

    // 饿汉式
    public SPUtils() {
    }

    private static SPUtils instance = new SPUtils();

    public static SPUtils getInstance() {
        return instance;
    }


    public static final String SP_NAME = "sp_name";

    /**
     * 获得sp对象
     *
     * @return
     */
    public SharedPreferences getSp() {
        if (sp == null) {
            sp = BaseApp.getAppContext().getSharedPreferences(SP_NAME,
                    Activity.MODE_PRIVATE);

        }
        return sp;
    }

    /**
     * 保存信息
     *
     * @param key
     * @param obj
     */
    public void save(String key, Object obj) {
        SharedPreferences.Editor editor = getSp().edit();
        if (obj instanceof Boolean) {
            editor.putBoolean(key, (Boolean) obj);
        } else if (obj instanceof Float) {
            editor.putFloat(key, (Float) obj);
        } else if (obj instanceof Integer) {
            editor.putInt(key, (Integer) obj);
        } else if (obj instanceof Long) {
            editor.putLong(key, (Long) obj);
        } else {
            editor.putString(key, (String) obj);
        }
        editor.commit();
    }

    /**
     * 删除指定数据(key)
     *
     * @param key
     */
    public boolean remove(String key) {
        return getSp().edit().remove(key).commit();
    }

    /**
     * 获取所有的键值对
     *
     * @return
     */
    public Map<String, ?> getAllKey() {
        return getSp().getAll();
    }

    /**
     * 删除所有数据
     *
     * @return
     */
    public boolean clear() {
        return getSp().edit().clear().commit();
    }

    /**
     * 检测key，对应的数据是否存在
     *
     * @param key
     * @return
     */
    public boolean contains(String key) {
        return getSp().contains(key);
    }
    public String getCookies() {
        return getSp().getString("cookies", null);
    }
    public void setCookies(String cookies) {
       save("cookies",cookies);
    }


//    /**
//     * 设置登陆信息
//     *
//     * @param info
//     * @return
//     */
//    public LoginBean setLoginInfo(String info) {
//        if (sp == null) {
//            sp = getSp();
//        }
//        sp.edit().putString("loginInfo", info).commit();
//        if (TextUtils.isEmpty(info)) {
//            loginInfo = null;
//        } else {
//            loginInfo = JSON.parseObject(info, LoginBean.class);
//        }
//        return loginInfo;
//    }
//
//    /**
//     * 查看登陆信息
//     *
//     * @return
//     */
//    public LoginBean getLoginInfo() {
//        if (sp == null || loginInfo == null) {
//            sp = getSp();
//            if (TextUtils.isEmpty(sp.getString("loginInfo", ""))) {
//                return null;
//            } else {
//                loginInfo = JSON.parseObject(sp.getString("loginInfo", ""), LoginBean.class);
//            }
//        }
//
//        return loginInfo;
//    }







}
