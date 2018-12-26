package com.pmcc.base_module.mvp;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.pmcc.base_module.down.DownCallBack;
import com.pmcc.base_module.net.BaseResponseBean;
import com.pmcc.base_module.utils.LogUtils;
import com.pmcc.base_module.widgets.MyProgressDialog;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.RequestBody;

/**
 * Created by ${zhangshuai} on 2018/9/10.
 * 2751157603@qq.com
 * presenter层数据处理，继承父类只能一个，实现可以多个
 */
public class DataPresenter extends BasePresenter<DataView> {


    private DataModel dataModel;
    private HashMap<String, Disposable> urlCancles;
    private MyProgressDialog myProgressDialog;

    /**
     * 初始化，并绑定view
     *
     * @param view
     */
    public DataPresenter(DataView view) {
        super(view);
        if (view instanceof Activity) {
            myProgressDialog = new MyProgressDialog((Activity) view);
        }
        dataModel = new DataModel();
        urlCancles = new LinkedHashMap<>();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy(LifecycleOwner owner) {
        cancleUrls();
        dataModel = null;
    }

    private void showProgress(String msg) {
        if (msg != null && myProgressDialog != null) {
            myProgressDialog.showMsg(msg);
        }
    }

    public void getDatas(final String url, Map<String, Object> parap, String msg) {
        showProgress(msg);
        getDatas(url, parap);
    }

    /**
     * get请求
     *
     * @param url
     * @param parap
     */
    public void getDatas(final String url, Map<String, Object> parap) {
        dataModel.getModel(url, parap, new Observer<BaseResponseBean>() {
            @Override
            public void onSubscribe(Disposable disposable) {
                urlCancles.put(url, disposable);
            }

            @Override
            public void onNext(BaseResponseBean responseBean) {
                LogUtils.d(JSON.toJSONString(responseBean));
                if (mView != null) {
                    myProgressDialog.dismiss();
                    if ("1".equals(responseBean.getResultCode())) {
                        if (TextUtils.isEmpty(responseBean.getObject())) {
                            mView.showSuccess(url, responseBean.getRows());
                        } else {
                            mView.showSuccess(url, responseBean.getObject());
                        }
                    } else {
                        mView.showError(url, "0", responseBean.getMsg());
                    }
                }
            }

            @Override
            public void onError(Throwable throwable) {
                LogUtils.d(throwable.getMessage());
                if (mView != null) {
                    myProgressDialog.dismiss();
                    mView.showError(url, "0", throwable.getMessage());
                }
            }

            @Override
            public void onComplete() {
                cancleUrl(url);
            }
        });
    }

    public void postDatas(final String url, Map<String, Object> parap, String msg) {
        showProgress(msg);
        postDatas(url, parap);
    }

    /**
     * post请求
     *
     * @param url
     * @param parap
     */
    public void postDatas(final String url, Map<String, Object> parap) {
        dataModel.postModel(url, parap, new Observer<BaseResponseBean>() {
            @Override
            public void onSubscribe(Disposable disposable) {
                urlCancles.put(url, disposable);
            }

            @Override
            public void onNext(BaseResponseBean responseBean) {
                LogUtils.d(JSON.toJSONString(responseBean));
                if (mView != null) {
                    myProgressDialog.dismiss();
                    mView.showSuccess(url, JSON.toJSONString(responseBean));
                }
            }

            @Override
            public void onError(Throwable throwable) {
                LogUtils.d(throwable.getMessage());
                if (mView != null) {
                    myProgressDialog.dismiss();
                    mView.showError(url, "0", throwable.getMessage());
                }
            }

            @Override
            public void onComplete() {
            }
        });
    }

    /**
     * 下载文件
     *
     * @param url
     */
    public void downFile(final String url,DownCallBack downCallBack) {
        dataModel.downFileModel(url, downCallBack);
    }

    public void postFileModel(final String url, Map<String, RequestBody> params, File[] files) {
        dataModel.postFileModel(url, params, files, new Observer<BaseResponseBean>() {
            @Override
            public void onSubscribe(Disposable disposable) {
                urlCancles.put(url, disposable);
            }

            @Override
            public void onNext(BaseResponseBean baseResponseBean) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * 取消单个网络请求(断开通道，取消请求)
     *
     * @param url
     */
    public void cancleUrl(String url) {
        if (urlCancles != null && urlCancles.containsKey(url)) {
            if (!urlCancles.get(url).isDisposed()) {
                urlCancles.get(url).dispose();
                urlCancles.remove(url);
            }
        }
    }

    /**
     * 取消该页面的所有网络请求（断开通道,取消请求）
     */
    private void cancleUrls() {
        if (urlCancles != null && urlCancles.size() > 0) {
            Set<Map.Entry<String, Disposable>> sets = urlCancles.entrySet();
            for (Map.Entry<String, Disposable> s : sets) {
                if (!s.getValue().isDisposed()) {
                    s.getValue().dispose();
                }
            }
        }
        urlCancles.clear();
        urlCancles = null;
    }
}
