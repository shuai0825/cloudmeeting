package com.pmcc.base_module.utils;

import android.os.Environment;

import com.pmcc.base_module.base.BaseApp;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 维护 所有缓存的路径
 *
 * @author wxj
 */
public class FileUtil {
    /**
     * 缓存数据的目录
     */
    private static final String CACHE = "cache";
    /**
     * 缓存图片的目录
     */
    private static final String ICON = "icon";
    /**
     * 下载目录
     */
    private static final String Down = "down";

    /**
     * 缓存路径的根目录
     */
    private static  String ROOT = BaseApp.getAppContext().getPackageName();
    private static long crrent;

    public static File getDir(String dir) {
        ROOT=ROOT.substring(ROOT.lastIndexOf("."));
        StringBuilder path = new StringBuilder();
        // sd卡可用
        if (isSDAvailable()) {
            path.append(Environment.getExternalStorageDirectory()
                    .getAbsolutePath());
            path.append(File.separator);
            path.append(ROOT);
            path.append(File.separator);
            path.append(dir);
        } else {
            path.append(UIUtils.getContext().getCacheDir().getAbsolutePath());
            path.append(File.separator);
            path.append(dir);
        }
        File file = new File(path.toString());
        if (!file.exists() || !file.isDirectory()) {
            file.mkdirs();
        }
        return file;
    }

    /**
     * sd卡可用
     *
     * @return
     */
    private static boolean isSDAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static File getCache() {
        return getDir(CACHE);
    }

    public static File getIcon() {
        return getDir(ICON);
    }

    public static File getDown() {
        return getDir(Down);
    }

    // 使用系统当前日期加以调整作为照片的名称
    public static String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }






}




