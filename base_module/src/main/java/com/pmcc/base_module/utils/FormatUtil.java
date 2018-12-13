package com.pmcc.base_module.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ${zhangshuai} on 2018/11/29.
 * 2751157603@qq.com
 */
public class FormatUtil {
    static String pwdFormat = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";//必有字母、数字
    static String phoneFormat = "[1]\\d{10}";
    static String cardFormat = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$";

    /**
     * 验证手机号
     *
     * @param phone
     * @return
     */
    public static boolean isPhoneFormat(String phone) {
        return phone.matches(phoneFormat);
    }

    /**
     * 验证密码
     *
     * @param pwd
     * @return
     */
    public static boolean isPwdFormat(String pwd) {
        return pwd.matches(pwdFormat);
    }

    /**
     * 验证身份证号
     *
     * @param card
     * @return
     */
    public static boolean isCardFormat(String card) {
        return card.matches(cardFormat);
    }

    /**
     * 格式日期，
     *
     * @param mTime，System.currentTimeMillis()获得当前时间
     * @param timeFormat，日期格式"yyyy-MM-dd             HH:mm:ss"
     * @return
     */
    public static String getTimeFormat(String mTime, String timeFormat) {
        SimpleDateFormat format = new SimpleDateFormat(timeFormat);
        return format.format(new Date(Long.parseLong(mTime)));
    }

    /**
     * 科学计数格式，并获取小数点后两位
     * @param inputMoney，注意运算时用BigDecimal
     * @return
     */
    public static String getMoneyFormat2(double inputMoney) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
        return decimalFormat.format(inputMoney);
    }
}
