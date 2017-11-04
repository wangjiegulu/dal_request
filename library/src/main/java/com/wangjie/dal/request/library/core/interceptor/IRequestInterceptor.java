package com.wangjie.dal.request.library.core.interceptor;


import com.wangjie.dal.request.library.core.XRequest;

/**
 * Author: wangjie Email: tiantian.china.2@gmail.com Date: 11/18/16.
 */
public interface IRequestInterceptor {
    void onRequestIntercept(XRequest xRequest) throws Throwable;
}
