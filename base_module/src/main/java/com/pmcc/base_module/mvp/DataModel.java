package com.pmcc.base_module.mvp;


import com.pmcc.base_module.net.BaseResponseBean;
import com.pmcc.base_module.net.DownCallBack;
import com.pmcc.base_module.net.RetrofitClient;

import java.io.File;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

/**
 * Created by ${zhangshuai} on 2018/9/10.
 * 2751157603@qq.com
 * 网络请求,获取数据
 * 可以对数据进行相关处理（接口未知，暂不处理）
 */
public class DataModel {
    /**
     * get获取数据
     *
     * @param url
     * @param parap
     */
    public void getModel(String url, Map<String, Object> parap, Observer<BaseResponseBean> observer) {
        RetrofitClient.getInstance().get(url, parap, observer);
    }

    /**
     * 带有缓存的
     *
     * @param url
     * @param parap
     * @param observer
     */
    public void getCatchModel(String url, Map<String, Object> parap, Observer<BaseResponseBean> observer) {
        RetrofitClient.getInstance().getCatch(url, parap, observer);
    }

    /**
     * post获取数据
     *
     * @param url
     * @param parap
     */
    public void postModel(String url, Map<String, Object> parap, Observer<BaseResponseBean> observer) {
        RetrofitClient.getInstance().post(url, parap, observer);
    }

    /**
     * 下载
     *
     * @param url
     * @param callBack
     */
    public void downFileModel(String url, DownCallBack callBack) {
        RetrofitClient.getInstance().downFile(url, callBack);
    }

    /**
     * 上传
     * @param url
     * @param params
     * @param files
     * @param observer
     */
    public void postFileModel(String url, Map<String, RequestBody> params, File[] files, Observer<BaseResponseBean> observer) {
        RetrofitClient.getInstance().postFile(url, params,files,observer);
    }


}

