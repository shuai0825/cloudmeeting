package com.pmcc.cloudmeeting;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.pmcc.base_module.base.BaseActivity;
import com.pmcc.base_module.down.DownCallBack;
import com.pmcc.base_module.mvp.DataPresenter;
import com.pmcc.base_module.mvp.DataView;
import com.pmcc.base_module.commen.UrlConstant;
import com.pmcc.base_module.down.DownLoadBuilder;
import com.pmcc.base_module.utils.LogUtils;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

public class MainActivity extends BaseActivity<DataPresenter> implements DataView {


    @BindView(R.id.main_login_bt)
    TextView mainLoginBt;
    @BindView(R.id.main_down_bt)
    TextView mainDownBt;
    @BindView(R.id.main_updata_bt)
    TextView mainUpdataBt;
    @BindView(R.id.main_dialog_bt)
    TextView mainDialogBt;
    private RxPermissions rxPermissions;
    private int requestCode;
    private String downUrl;

    @Override
    protected DataPresenter createPresenter() {
        return new DataPresenter(this);
    }

    @Override
    public boolean setNoScreenFit() {
        return true;
    }

    @Override
    public int getLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() throws Exception {
        rxPermissions = new RxPermissions(this);

    }


    @OnClick({R.id.main_login_bt, R.id.main_down_bt, R.id.main_updata_bt, R.id.main_dialog_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main_login_bt:
                HashMap<String, Object> parap = new HashMap<>();
                parap.put("phone", "admin");
                parap.put("pwd", "123");
                mPresenter.postDatas(UrlConstant.login, parap, "登录中");
                break;
            case R.id.main_down_bt:
                String downUrl = "http://wx.pmcc.com.cn:8893/uploadFile/appVersion/2018-12-10/c4bbd7d7f83d40ca82a0dd6515b727de.apk";
                mPresenter.downFile(downUrl, new DownCallBack() {
                    @Override
                    public void onProgress(boolean finish, String path, final long total, final long current) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showToast("" + (int) (current * 100 / total));
                            }
                        });
                    }

                    @Override
                    public void onError(final Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showToast("" + e.getMessage());
                            }
                        });
                    }
                });
                break;
            case R.id.main_updata_bt:
                mPresenter.getDatas(UrlConstant.getNewVersion, new HashMap<String, Object>(), "加载中");
                break;
            case R.id.main_dialog_bt:
                break;
        }
    }

    private void downLoad() {
        new DownLoadBuilder.Builder(MainActivity.this)
                .addUrl(downUrl)
                .isWiFi(true)
                .addDownLoadName("测试demo")
                .addDscription("开始下载")
                .builder();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (requestCode == 1) {
            if (rxPermissions.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                requestCode = 0;
                showToast("版本更新中");
                downLoad();
            }
        }
    }

    private void showPermissiion() {
        final AlertDialog.Builder dialogView = new AlertDialog.Builder(this);
        dialogView.setTitle("提示");
        dialogView.setMessage("该软件是音视频类,需要用户在权限管理中，授予相机、麦克风和储存空间权限");
        dialogView.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent localIntent = new Intent();
                localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                localIntent.setData(Uri.fromParts("package", getPackageName(), null));
                startActivity(localIntent);
            }
        });
        dialogView.show();
    }

    @Override
    public void showSuccess(String url, String info) {
        LogUtils.d(info);
        switch (url) {
            case UrlConstant.getNewVersion:
                downUrl = UrlConstant.base + JSON.parseObject(info).getString("versionSrc");
                //http://wx.pmcc.com.cn:8893/uploadFile/appVersion/2018-12-10/c4bbd7d7f83d40ca82a0dd6515b727de.apk
                LogUtils.d(downUrl);
                rxPermissions.requestEachCombined(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            downLoad();
                        } else {
                            requestCode = 1;
                            showPermissiion();
                        }
                    }
                });
                break;
        }


    }

    @Override
    public void showError(String url, String code, String msgInfo) {

    }
}
