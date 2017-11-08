package com.wangjie.dal.request;


import com.wangjie.dal.request.core.interceptor.IOriginResponseInterceptor;
import com.wangjie.dal.request.core.interceptor.IRequestInterceptor;
import com.wangjie.dal.request.core.interceptor.IResponseInterceptor;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: wangjie Email: tiantian.china.2@gmail.com Date: 11/18/16.
 */
public class XHttpManager {
    private boolean isDebug = false;
    /**
     * 请求拦截器
     */
    private List<IRequestInterceptor> requestInterceptorList = new ArrayList<>();
    /**
     * 响应原始byte数据拦截器
     */
    private IOriginResponseInterceptor originResponseInterceptor;

    /**
     * 响应拦截器
     */
    private List<IResponseInterceptor> responseInterceptorList = new ArrayList<>();

    public static XHttpManager getInstance() {
        return Holder.instance;
    }

    public XHttpManager setDebug(boolean debug){
        this.isDebug = debug;
        return this;
    }

    public boolean isDebug() {
        return isDebug;
    }

    public XHttpManager addRequestInterceptor(IRequestInterceptor requestInterceptor) {
        if (!requestInterceptorList.contains(requestInterceptor)) {
            requestInterceptorList.add(requestInterceptor);
        }
        return this;
    }

    public XHttpManager addResponseInterceptor(IResponseInterceptor responseInterceptor) {
        if (!responseInterceptorList.contains(responseInterceptor)) {
            responseInterceptorList.add(responseInterceptor);
        }
        return this;
    }

    public IOriginResponseInterceptor getOriginResponseInterceptor() {
        return originResponseInterceptor;
    }

    public XHttpManager setOriginResponseInterceptor(IOriginResponseInterceptor originResponseInterceptor) {
        this.originResponseInterceptor = originResponseInterceptor;
        return this;
    }

    public List<IRequestInterceptor> getRequestInterceptorList() {
        return requestInterceptorList;
    }

    public List<IResponseInterceptor> getResponseInterceptorList() {
        return responseInterceptorList;
    }

    private static class Holder {
        private static XHttpManager instance = new XHttpManager();
    }
}
