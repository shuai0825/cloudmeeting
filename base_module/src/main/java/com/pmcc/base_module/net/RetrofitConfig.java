package com.pmcc.base_module.net;


import com.pmcc.base_module.base.BaseApp;
import com.pmcc.base_module.utils.LogUtils;
import com.pmcc.base_module.utils.NetworkUtil;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ${zhangshuai} on 2018/11/29.
 * 2751157603@qq.com
 * retrofit配置请求参数
 */
public class RetrofitConfig {
    //缓存目录
    public static Cache fileCache = new Cache(new File(BaseApp.getAppContext().getCacheDir(), "HttpCache"), 1024 * 1024 * 10);

    /**
     * 设置缓存
     */
    public static Interceptor addCacheInterceptor() {
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (NetworkUtil.getNetState() == -1) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (NetworkUtil.getNetState() != -1) {
                    // 有网络时 设置缓存超时时间0个小时 ,意思就是不读取缓存数据,只对get有用,post没有缓冲
                    int maxAge = 0;
                    response.newBuilder()
                            .removeHeader("Pragma")
                            .removeHeader("Cache-Control")// 清除头信息
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .build();
                } else {
                    // 无网络时，设置超时为1天  只对get有用,post没有缓冲
                    int maxStale = 60 * 60 * 24 * 1;
                    response.newBuilder()
                            .removeHeader("Cache-Control")
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public, only-if-cached, max-stale=" +
                                    maxStale)
                            .build();
                }
                return response;
            }
        };
        return cacheInterceptor;
    }

    /**
     * 设置公共参数、请求头
     */
    public static Interceptor addQueryParameterInterceptor() {
        Interceptor addQueryParameterInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .header("Content-Type", "application/json")
                        .addHeader("Connection", "close")
                        .build();
                return chain.proceed(request);
            }
        };
        return addQueryParameterInterceptor;
    }

    /**
     * 请求、返回参数打印
     */
    public static Interceptor addLoggingInterceptor() {
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                //拿到request实例在此对请求做需要的设置
                Request request = chain.request();
                long t1 = System.nanoTime();
                LogUtils.i(String.format("Sending request %s on %s%n%s",
                        request.url(), chain.connection(), request.headers()));
                //发送request请求
                Response response = chain.proceed(request);
                //得到请求后的response实例，做相应操作
                long t2 = System.nanoTime();
                LogUtils.i(String.format("Received response for %s in %.1fms%n%s",
                        response.request().url(), (t2 - t1) / 1e6d, response.headers()));
                return response;
            }
        };
        return interceptor;
    }


    ;
}
