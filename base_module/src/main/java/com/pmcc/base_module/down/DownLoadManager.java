package com.pmcc.base_module.down;

import com.pmcc.base_module.utils.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;


/**
 * Created by Tamic on 2016-07-11.
 */
public class DownLoadManager {


    private static final String TAG = "DownLoadManager";

    private static String APK_CONTENTTYPE = "application/vnd.android.package-archive";

    private static String PNG_CONTENTTYPE = "image/png";

    private static String JPG_CONTENTTYPE = "image/jpg";

    private static String fileSuffix = "";
    private static DownLoadManager downLoadManager;
    private DownCallBack callBack;

    private DownLoadManager(DownCallBack callBack) {
        this.callBack = callBack;
    }

    ;

    public static DownLoadManager getInstance(DownCallBack callBack) {
        if (downLoadManager == null) {
            downLoadManager = new DownLoadManager(callBack);
        }
        return downLoadManager;
    }


    public void writeResponseBodyToDisk(ResponseBody body) {

        String type = body.contentType().toString();

        if (type.equals(APK_CONTENTTYPE)) {
            fileSuffix = ".apk";
        } else if (type.equals(PNG_CONTENTTYPE)) {
            fileSuffix = ".png";
        } else if (type.equals(JPG_CONTENTTYPE)) {
            fileSuffix = ".jpg";
        }
        // 其他同上 自己判断加入

        final String name = System.currentTimeMillis() + fileSuffix;
        final String path = FileUtil.getDown() + File.separator + name;
        callBack.onProgress(false,path,0,0);

        try {
            File futureStudioIconFile = new File(path);

            if (futureStudioIconFile.exists()) {
                futureStudioIconFile.delete();
            }

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long totalSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                    callBack.onProgress(false,path,totalSize, fileSizeDownloaded);
                }

                outputStream.flush();
                callBack.onProgress(true,path,totalSize, fileSizeDownloaded);
            } catch (Exception e) {
                callBack.onError(e);
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            callBack.onError(e);

        }
    }


}
