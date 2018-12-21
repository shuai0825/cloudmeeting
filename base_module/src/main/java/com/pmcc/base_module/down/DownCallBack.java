package com.pmcc.base_module.down;

/**
 * Created by ${zhangshuai} on 2018/12/20.
 * 2751157603@qq.com
 */
public  interface DownCallBack {
   void onProgress(boolean finish,String path,long total,long current);
    void onError(Throwable e);
}
