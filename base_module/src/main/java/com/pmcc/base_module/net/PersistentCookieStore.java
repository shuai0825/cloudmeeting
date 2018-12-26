package com.pmcc.base_module.net;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.pmcc.base_module.utils.LogUtils;
import com.pmcc.base_module.utils.SPUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.CookieStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * Created by Tamic on 2016-06-09.
 * {@link # https://github.com/NeglectedByBoss/RetrofitClient}
 */
public class PersistentCookieStore {


    /**
     * 添加cookies
     *
     * @param url
     * @param cookies
     */
    public void addCookies(HttpUrl url, List<Cookie> cookies) {
        String cookieString = "";
        for (Cookie cookie : cookies) {
            cookieString = cookieString + encodeCookie(new SerializableCookie(cookie)) + ",";
        }
        SPUtils.getInstance().setCookies(cookieString);
    }

    /**
     * 获取cookies
     *
     * @return
     */
    public List<Cookie> getCookies() {
        ArrayList<Cookie> cookies = new ArrayList<>();
        if (!TextUtils.isEmpty(SPUtils.getInstance().getCookies())) {
            String[] split = SPUtils.getInstance().getCookies().split(",");
            for (int i = 0; i < split.length; i++) {
                cookies.add(decodeCookie(split[i]));
            }
        }
        return cookies;
    }


    /**
     * 将cookies反序列化成字符串
     *
     * @param cookie
     * @return
     */
    protected String encodeCookie(SerializableCookie cookie) {
        if (cookie == null)
            return null;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(os);
            outputStream.writeObject(cookie);
        } catch (Exception e) {
            return null;
        }

        return byteArrayToHexString(os.toByteArray());
    }


    /**
     * 将字符串反序列化成cookies
     *
     * @param cookieString cookies string
     * @return cookie object
     */
    protected Cookie decodeCookie(String cookieString) {
        byte[] bytes = hexStringToByteArray(cookieString);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        Cookie cookie = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            cookie = ((SerializableCookie) objectInputStream.readObject()).getCookie();
        } catch (Exception exception) {

        }

        return cookie;
    }


    /**
     * 二进制数组转十六进制字符串
     *
     * @param bytes byte array to be converted
     * @return string containing hex values
     */
    protected String byteArrayToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte element : bytes) {
            int v = element & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase(Locale.US);
    }

    /**
     * 十六进制字符串转二进制数组
     *
     * @param hexString string of hex-encoded values
     * @return decoded byte array
     */
    protected byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character.digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }


}
