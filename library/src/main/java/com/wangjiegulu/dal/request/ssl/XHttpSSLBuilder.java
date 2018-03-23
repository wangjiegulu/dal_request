package com.wangjiegulu.dal.request.ssl;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

/**
 * Author: wangjie Email: tiantian.china.2@gmail.com Date: 12/7/16.
 */
public abstract class XHttpSSLBuilder {
    protected HostnameVerifier hostnameVerifier;
    protected SSLSocketFactory sslSocketFactory;
    protected X509TrustManager x509TrustManager;

    public HostnameVerifier getHostnameVerifier() throws Exception {
        ensureSSLBuilder();
        return hostnameVerifier;
    }

    public SSLSocketFactory getSslSocketFactory() throws Exception {
        ensureSSLBuilder();
        return sslSocketFactory;
    }

    public X509TrustManager getX509TrustManager() throws Exception {
        ensureSSLBuilder();
        return x509TrustManager;
    }

    protected abstract void ensureSSLBuilder() throws Exception;

//    private OkHttpClient getUnsafeOkHttpClient() {
//        try {
//            ensureSSLBuilder();
//            OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient().newBuilder();
//            okHttpClientBuilder.sslSocketFactory(sslSocketFactory, x509TrustManager);
//            okHttpClientBuilder.protocols(Arrays.asList(Protocol.HTTP_1_1));
//            okHttpClientBuilder.hostnameVerifier((hostname, session) -> true);
//            return okHttpClientBuilder.build();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

}
