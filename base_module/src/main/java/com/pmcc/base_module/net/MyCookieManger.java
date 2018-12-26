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
public class MyCookieManger implements CookieJar {
    private static PersistentCookieStore cookieStore;

    /**
     * Mandatory constructor for the NovateCookieManger
     */
    public MyCookieManger() {
        if (cookieStore == null) {
            cookieStore = new PersistentCookieStore();
        }
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies != null && cookies.size() > 0) {
            cookieStore.addCookies(url, cookies);
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = cookieStore.getCookies();
        return cookies;
    }


}