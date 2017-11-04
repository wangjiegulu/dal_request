package com.wangjie.dal.request.library.core.interceptor;


import com.wangjie.dal.request.library.core.XRequest;
import com.wangjie.dal.request.library.response.DalBaseResponse;

/**
 * Author: wangjie Email: tiantian.china.2@gmail.com Date: 11/18/16.
 */
public interface IResponseInterceptor {
    void onResponseIntercept(XRequest xRequest, DalBaseResponse dalBaseResponse) throws Throwable;
}
