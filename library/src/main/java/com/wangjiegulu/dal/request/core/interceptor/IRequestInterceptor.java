package com.wangjiegulu.dal.request.core.interceptor;


import com.wangjiegulu.dal.request.core.request.XRequest;

/**
 * Author: wangjie Email: tiantian.china.2@gmail.com Date: 11/18/16.
 */
public interface IRequestInterceptor {
    void onRequestIntercept(XRequest xRequest) throws Throwable;
}
