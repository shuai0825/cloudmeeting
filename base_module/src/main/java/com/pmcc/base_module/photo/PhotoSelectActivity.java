package com.pmcc.base_module.photo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.pmcc.base_module.R;
import com.pmcc.base_module.base.BaseActivity;
import com.pmcc.base_module.mvp.BasePresenter;

public class PhotoSelectActivity extends BaseActivity implements View.OnClickListener {


    private static final int SELECTPHOTO = 11;
    private TextView photoSelect1;
    private TextView photoSelect2;
    private TextView photoSelect3;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public boolean setNoScreenFit() {
        return true;
    }

    @Override
    public int getLayoutResID() {
        return R.layout.activity_photo_select;
    }

    @Override
    protected void initView() throws Exception {
        photoSelect1 = findViewById(R.id.photo_select_1);
        photoSelect2 = findViewById(R.id.photo_select_2);
        photoSelect3 = findViewById(R.id.photo_select_3);
    }

    @Override
    protected void initListener() throws Exception {
        photoSelect1.setOnClickListener(this);
        photoSelect2.setOnClickListener(this);
        photoSelect3.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.photo_select_1) {

        } else if (i == R.id.photo_select_2) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_PICK);
            intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            //  intent.setData(MediaStore.Video.Media.EXTERNAL_CONTENT_URI);选取视频
            startActivityForResult(intent, SELECTPHOTO);
        } else {
            finish();
            overridePendingTransition(R.anim.ac_vertical_in, R.anim.ac_vertical_out);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.ac_vertical_in, R.anim.ac_vertical_out);
    }
}
