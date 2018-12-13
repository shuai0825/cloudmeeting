package com.pmcc.base_module.net;

import io.reactivex.disposables.Disposable;

/**
 * Created by ${zhangshuai} on 2018/12/12.
 * 2751157603@qq.com
 */
public abstract class DownCallBack {
    public void onStart(Disposable d){}

    public void onCompleted(){}

    abstract public void onError(Throwable e);

    public void onProgress(long fileSize,long downSize){}

    abstract public void onSucess(String path, String name, long fileSize);
}
