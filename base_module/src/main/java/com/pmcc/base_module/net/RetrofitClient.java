package com.pmcc.base_module.net;

import com.pmcc.base_module.commen.UrlConstant;
import com.pmcc.base_module.down.DownCallBack;
import com.pmcc.base_module.down.DownSubscriber;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;

/**
 * Created by ${zhangshuai} on 2018/12/12.
 * 2751157603@qq.com
 */
public class RetrofitClient {
    private static final int DEFAULT_TIMEOUT = 5;

    private BaseApiService apiService;
    private static Retrofit retrofit;


    private static RetrofitClient sNewInstance;


    private RetrofitClient() {

    }

    public static RetrofitClient getInstance() {
        if (sNewInstance == null) {
            sNewInstance = new RetrofitClient();
        }
        return sNewInstance;
    }

    /**
     * 是否设置cook、缓存
     *
     * @param setCook
     * @param setCatch
     * @return
     */
    private Retrofit getRetrofit(boolean setCook, boolean setCatch) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (setCook) {//对其他请求没影响，该cook可以打开
            builder.cookieJar(new MyCookieManger());
        }
        if (setCatch) {
            builder.cache(RetrofitConfig.fileCache)
                    .addInterceptor(RetrofitConfig.addCacheInterceptor());
        }

        OkHttpClient okHttpClient = builder.addInterceptor(RetrofitConfig.addQueryParameterInterceptor())
                .addInterceptor(RetrofitConfig.addLoggingInterceptor())
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();


        return new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(UrlConstant.baseurl)
                .build();

    }

    /**
     * 普通get
     *
     * @param url
     * @param parap
     * @param observer
     */
    public void get(String url, Map<String, Object> parap, Observer<BaseResponseBean> observer) {
        getRetrofit(false, false).create(BaseApiService.class)
                .getData(url, parap)
                .subscribeOn(Schedulers.io())//发射事件,io线程网络请求,多次指定时,第一次有效
                .observeOn(AndroidSchedulers.mainThread())//接收事件，ui线程处理
                .subscribe(observer);
    }

    /**
     * 带有缓存的get
     *
     * @param url
     * @param parap
     * @param observer
     */
    public void getCatch(String url, Map<String, Object> parap, Observer<BaseResponseBean> observer) {
        getRetrofit(false, true).create(BaseApiService.class)
                .getData(url, parap)
                .subscribeOn(Schedulers.io())//发射事件,io线程网络请求,多次指定时,第一次有效
                .observeOn(AndroidSchedulers.mainThread())//接收事件，ui线程处理
                .subscribe(observer);
    }

    /**
     * post请求
     *
     * @param url
     * @param parap
     * @param observer
     */
    public void post(String url, Map<String, Object> parap, Observer<BaseResponseBean> observer) {
        getRetrofit(false, false).create(BaseApiService.class)
                .postData(url, parap)
                .subscribeOn(Schedulers.io())//发射事件,io线程网络请求,多次指定时,第一次有效
                .observeOn(AndroidSchedulers.mainThread())//接收事件，ui线程处理
                .subscribe(observer);
    }

    /**
     * 下载
     *
     * @param url
     */
    public void downFile(String url, DownCallBack downCallBack) {
        getRetrofit(false, false).create(BaseApiService.class).downFile(url)
                .subscribeOn(Schedulers.io())//发射事件,io线程网络请求,多次指定时,第一次有效
                .observeOn(Schedulers.io())
                .subscribe(new DownSubscriber<ResponseBody>(downCallBack));


    }

    /**
     * \上传文件
     *
     * @param url
     */
    public void postFile(String url, Map<String, RequestBody> params, File[] files, Observer<BaseResponseBean> observer) {
        for (int i = 0; i < files.length; i++) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), files[i]);
            params.put("file\"; filename=\"" + files[i].getName() + "", requestBody);
        }
        getRetrofit(false, false).create(BaseApiService.class)
                .postFile(url, params)
                .subscribeOn(Schedulers.io())//发射事件,io线程网络请求,多次指定时,第一次有效
                .observeOn(AndroidSchedulers.mainThread())//接收事件，ui线程处理
                .subscribe(observer);
    }
}
