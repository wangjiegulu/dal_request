package com.wangjiegulu.dal.request.core.interceptor;


import com.wangjiegulu.dal.request.core.request.XRequest;

/**
 * Author: wangjie Email: tiantian.china.2@gmail.com Date: 11/18/16.
 */
public interface IResponseRetryInterceptor {
    boolean onResponseRetryIntercept(XRequest xRequest, Integer retryCount, Throwable throwable) throws Exception;
}
