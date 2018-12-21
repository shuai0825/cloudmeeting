package com.pmcc.base_module.widgets;

import android.content.Context;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;

/**
 * Created by ${zhangshuai} on 2018/11/16.
 * 2751157603@qq.com
 * 输入资金的editext，保留两位小数,
 * 获取输入内容时，用getMoneyEditText（），
 * 如用getText(),注意用Double.parse()转化下避免出现类似（.23）的情况
 */
public class MoneyEditText extends android.support.v7.widget.AppCompatEditText {
    public MoneyEditText(Context context) {
        super(context);
        initLister();
    }

    public MoneyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLister();
    }

    public MoneyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLister();
    }

    private void initLister() {
        //只可以输入小数跟"."
        setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER);

    }

    public String getMoneyEditText() {
        String moneyInput = getText().toString().trim();
        if ("".equals(moneyInput)) {
            return "";
        } else {
            return String.valueOf(Double.parseDouble(moneyInput));
        }
    }

    @Override
    protected void onTextChanged(CharSequence s, int start, int lengthBefore, int lengthAfter) {
        if (TextUtils.isEmpty(s)) {
            return;
        }
        //第一个字符为小数点
        if (s.toString().equals(".")) {
            setText("0.");
            setSelection(getText().toString().length());
        }
        //去除01、00等首数字为0
        if (s.length() > 1 && "0".equals("" + s.charAt(0)) && !".".equals("" + s.charAt(1))) {
            setText(s.subSequence(1, s.length()));
        }
        //只能输入两位小数
        if (s.toString().contains(".")) {
            int lastPosition = s.toString().lastIndexOf(".");
            if (s.toString().substring(lastPosition, s.toString().length()).length() > 3) {
                setText(s.subSequence(0, lastPosition + 3));
                setSelection(getText().toString().length());
            }
        }
    }


}