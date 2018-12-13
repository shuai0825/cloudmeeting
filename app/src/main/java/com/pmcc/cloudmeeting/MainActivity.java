package com.pmcc.cloudmeeting;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;

import com.pmcc.base_module.base.BaseActivity;
import com.pmcc.base_module.base.BaseApp;
import com.pmcc.base_module.mvp.BasePresenter;
import com.pmcc.base_module.mvp.DataPresenter;
import com.pmcc.base_module.mvp.DataView;
import com.pmcc.base_module.photo.PhotoSelectActivity;
import com.pmcc.base_module.utils.DeviceUtil;
import com.pmcc.base_module.utils.FileUtil;
import com.pmcc.base_module.utils.LogUtils;
import com.pmcc.base_module.utils.NetworkUtil;
import com.pmcc.base_module.utils.UIUtils;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends BaseActivity<DataPresenter> implements DataView {


    @Override
    protected DataPresenter createPresenter() {
        return new DataPresenter(this);
    }

    @Override
    public boolean setNoScreenFit() {
        return false;
    }

    @Override
    public int getLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() throws Exception {
        Map parap = new HashMap<>();
        parap.put("phone", 1329504);
        parap.put("pwd", 123);
       mPresenter.postDatas("http://wx.pmcc.com.cn:8893/app/member/login",parap);
       // mPresenter.getDatas("http://wx.pmcc.com.cn:8893/app/appVersion/getNewVersion",parap,"加载");
        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setAction(Intent.ACTION_PICK);
//                intent.setData(MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(intent, 13);

//                Intent intent = new Intent();
//                intent.setAction(Intent.ACTION_PICK);
//                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(intent, 14);

                Intent intent = new Intent(MainActivity.this, PhotoSelectActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.ac_vertical_in, R.anim.ac_vertical_out);
            }
        });

    }

    @Override
    public void showSuccess(String url, String info) {

    }

    @Override
    public void showError(String url, String code, String msgInfo) {

    }
}
