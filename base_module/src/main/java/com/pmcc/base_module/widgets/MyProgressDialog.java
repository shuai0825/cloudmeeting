package com.pmcc.base_module.widgets;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.pmcc.base_module.R;


/**
 * Created by ${zhangshuai} on 2018/9/10.
 * 2751157603@qq.com
 */
public class MyProgressDialog extends ProgressDialog {
    private  ImageView myDialogIv;
    private TextView myDialogTv;

    public MyProgressDialog(@NonNull Context context) {
        super(context, R.style.MyProgressDialog);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_my_progress);
        myDialogIv = findViewById(R.id.my_dialog_iv);
        myDialogTv = findViewById(R.id.my_dialog_tv);
    }

    public void showMsg(String msg) {
        show();
        if (msg == null) {
            myDialogTv.setText("加载中...");
        } else {
            myDialogTv.setText(msg);
        }
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            ObjectAnimator rotation = ObjectAnimator.ofFloat(myDialogIv, "rotation", 0, 360);
            rotation.setDuration(1500);
            rotation.setInterpolator(new LinearInterpolator());
            rotation.setRepeatCount(-1);
            rotation.start();
        } else {
            myDialogIv.clearAnimation();
        }
    }
}
