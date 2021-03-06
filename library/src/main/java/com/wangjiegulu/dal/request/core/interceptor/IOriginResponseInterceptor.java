package com.wangjiegulu.dal.request.core.interceptor;


import com.wangjiegulu.dal.request.core.request.XRequest;

/**
 * Author: wangjie Email: tiantian.china.2@gmail.com Date: 11/18/16.
 */
public interface IOriginResponseInterceptor {
    /**
     * response原始数据拦截方法，可以在这里进行对响应数据的解密等操作
     */
    byte[] onOriginResponseIntercept(XRequest xRequest, byte[] responseBytes) throws Throwable;
}
