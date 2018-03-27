package com.wangjie.dal.request.sample;

import com.wangjiegulu.dal.request.core.request.XRequest;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 11/15/16.
 */
public class XRequestCreator {

    /**
     * 默认https
     */
    public XRequest createRequest(String httpUrl) {
//        return XRequest.create(WebApiConstants.formatHttpsWebApi(httpUrl));
        return XRequest.create(httpUrl);
    }

//
//    public XRequest createHttpsRequest(String httpUrl) {
//        return XRequest.create(WebApiConstants.formatHttpsWebApi(httpUrl));
//    }

}
