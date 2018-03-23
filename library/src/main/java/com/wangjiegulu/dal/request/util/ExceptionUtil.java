package com.wangjiegulu.dal.request.util;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

/**
 * Author: wangjie Email: tiantian.china.2@gmail.com Date: 12/9/16.
 */
public final class ExceptionUtil {
    private ExceptionUtil() {

    }

    /**
     * 是否是网络异常
     */
    public static boolean isNetworkError(Throwable throwable) {
        return (throwable instanceof UnknownHostException) || (throwable instanceof TimeoutException)
                || (throwable instanceof SocketException) || (throwable instanceof SocketTimeoutException);
    }


    /**
     * 是否是可以断点下载的异常
     */
    public static boolean isNetworkTimoutError(Throwable throwable) {
        return (throwable instanceof TimeoutException)
                || (throwable instanceof SocketTimeoutException);
    }
}
