package com.wangjiegulu.dal.request.util;

import android.util.Log;

import java.io.Closeable;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 23/03/2018.
 */
public class XIOUtil {
    private static final String TAG = XIOUtil.class.getSimpleName();

    public static void closeIO(Closeable... closeables) {
        if (null == closeables) {
            return;
        }
        for (Closeable closeable : closeables) {
            if (null != closeable) {
                try {
                    closeable.close();
                } catch (Throwable throwable) {
                    Log.e(TAG, "", throwable);
                }
            }
        }
    }
}
