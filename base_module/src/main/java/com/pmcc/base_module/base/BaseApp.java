package com.pmcc.base_module.base;

import android.app.Application;
import android.content.Context;

import com.pmcc.base_module.utils.NeverCrash;


/**
 * Created by ${zhangshuai} on 2018/11/28.
 * 2751157603@qq.com
 */
public class BaseApp extends Application {
   private static Context appContext;
   @Override
   public void onCreate() {
      super.onCreate();
      appContext=this;
      initArouter();
      initCrash();

   }
   public static Context getAppContext() {
      return appContext;
   }

   /**
    * 异常捕获
    */
   private void initCrash() {
      NeverCrash.init(new NeverCrash.CrashHandler() {
         @Override
         public void uncaughtException(Thread t, Throwable e) {
//                Intent intent = new Intent(instance, ErrorActivity.class);
//                intent.putExtra("error", Log.getStackTraceString(e));
//                startActivity(intent);
         }
      });
   }

   /**
    * 初始化arouter
    */
   private void initArouter() {

   }



}