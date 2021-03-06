package com.pmcc.base_module.utils;

import android.widget.Toast;

import com.pmcc.base_module.base.BaseApp;


/**
 * 动态toast
 */
public class ToastUtils {
    private static Toast toast;

    /**
     * 提示，用全局context
     * @param text
     */
    public static void showToast(String text) {
        if (toast == null) {
            toast = Toast.makeText(BaseApp.getAppContext(), text, Toast.LENGTH_SHORT);
        }
        toast.setText(text);
        //  toast.setGravity(Gravity.BOTTOM, 0, UIUtils.getDimention(R.dimen.dp_toast_bottom));
        toast.show();
    }

}