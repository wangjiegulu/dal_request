package com.wangjie.dal.request.library;


import okhttp3.Request;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 7/6/15.
 */
public class XRequestBuilder extends Request.Builder {
//    private static final String TAG = XRequestBuilder.class.getSimpleName();

    public static XRequestBuilder create() {
        return new XRequestBuilder();
    }

//    @Override
//    public Request.Builder url(HttpUrl url) {
//        return super.url(url);
//    }

//    @Override
//    public Request.Builder url(String url) {
//        return super.url(url);
//    }
//
//    @Override
//    public Request.Builder url(URL url) {
//        return super.url(url);
//    }

//    @Override
//    public Request build() {
//        return super.build();
//    }
}
