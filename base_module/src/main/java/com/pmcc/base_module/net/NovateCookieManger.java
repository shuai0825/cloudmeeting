package com.pmcc.base_module.net;

import android.content.Context;

import com.pmcc.base_module.base.BaseApp;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Created by ${zhangshuai} on 2018/12/11.
 * 2751157603@qq.com
 */
public class NovateCookieManger implements CookieJar {
    private static final String TAG = "NovateCookieManger";
    private static Context mContext;
    private static PersistentCookieStore cookieStore;

    /**
     * Mandatory constructor for the NovateCookieManger
     */
    public NovateCookieManger() {
        mContext = BaseApp.getAppContext();
        if (cookieStore == null) {
            cookieStore = new PersistentCookieStore(mContext);
        }
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies != null && cookies.size() > 0) {
            for (Cookie item : cookies) {
                cookieStore.add(url, item);
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = cookieStore.get(url);
        return cookies;
    }


}
