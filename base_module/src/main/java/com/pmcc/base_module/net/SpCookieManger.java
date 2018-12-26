package com.pmcc.base_module.net;

import android.content.Context;

import com.pmcc.base_module.base.BaseApp;
import com.pmcc.base_module.utils.LogUtils;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Created by ${zhangshuai} on 2018/12/11.
 * 2751157603@qq.com
 */
public class SpCookieManger implements CookieJar {


    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        LogUtils.d(""+cookies.toString());
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        LogUtils.d(""+url);
        return null;
    }
}