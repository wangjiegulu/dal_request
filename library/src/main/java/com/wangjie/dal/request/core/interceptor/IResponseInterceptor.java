package com.wangjie.dal.request.core.interceptor;


import com.wangjie.dal.request.core.request.XRequest;

/**
 * Author: wangjie Email: tiantian.china.2@gmail.com Date: 11/18/16.
 */
public interface IResponseInterceptor {
    void onResponseIntercept(XRequest xRequest, Object dalBaseResponse) throws Throwable;
}
