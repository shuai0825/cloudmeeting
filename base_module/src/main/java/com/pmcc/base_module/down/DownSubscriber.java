package com.pmcc.base_module.down;



import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * Created by ${zhangshuai} on 2018/12/12.
 * 2751157603@qq.com
 */
public  class DownSubscriber<ResponseBody> implements Observer<ResponseBody> {
    DownCallBack callBack;
    public DownSubscriber(DownCallBack callBack) {
        this.callBack = callBack;
    }


    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public void onNext(ResponseBody responseBody) {
        DownLoadManager.getInstance(callBack).writeResponseBodyToDisk((okhttp3.ResponseBody) responseBody);
    }

    @Override
    public void onError(Throwable e) {
        if (callBack != null) {
            callBack.onError(e);
        }
    }

    @Override
    public void onComplete() {

    }
}
