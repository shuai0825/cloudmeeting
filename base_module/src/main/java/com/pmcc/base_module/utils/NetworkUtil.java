package com.pmcc.base_module.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.pmcc.base_module.base.BaseApp;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 网络判断状态
 */
public class NetworkUtil {

  public static int NET_CNNT_MOBILE_OK = 0; // 移动网络
  public static int NET_CNNT_WIFI_OK = 1; // wifi
  public static int NET_NOT_PREPARE = -1; // 网络未连接

  private static int TIMEOUT = 3000; // TIMEOUT


  /**
   * 返回当前网络状态
   *
   * @return
   */
  public static int getNetState() {
    try {
      ConnectivityManager connectivity = (ConnectivityManager) BaseApp.getAppContext()
              .getSystemService(Context.CONNECTIVITY_SERVICE);
      if (connectivity != null) {
        NetworkInfo networkinfo = connectivity.getActiveNetworkInfo();
        if (networkinfo != null) {
          if (networkinfo.isAvailable() && networkinfo.isConnected()) {
            if (networkinfo.getType() == ConnectivityManager.TYPE_MOBILE) {//移动网络
              return NET_CNNT_MOBILE_OK;
            } else if (networkinfo.getType() == ConnectivityManager.TYPE_WIFI) {
              return NET_CNNT_WIFI_OK;
            }
          } else {
            return NET_NOT_PREPARE;
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return NET_NOT_PREPARE;
  }

  /**
   * 为网络时，调用该弹窗，设置网络
   *
   * @param context
   */
  public static void showSetNetDialog(final Context context) {
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder.setTitle("网络设置提示").setMessage("网络连接不可用,是否进行设置?");
    builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {
        context.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
      }
    });
    builder.setNegativeButton("取消", null);
    builder.show();
  }


} 