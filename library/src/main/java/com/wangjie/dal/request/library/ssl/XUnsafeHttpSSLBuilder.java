package com.wangjie.dal.request.library.ssl;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Author: wangjie Email: tiantian.china.2@gmail.com Date: 12/7/16.
 */
public class XUnsafeHttpSSLBuilder extends XHttpSSLBuilder {
    @Override
    protected void ensureSSLBuilder() throws Exception {
        if (null != hostnameVerifier) {
            return;
        }
        synchronized (this) {
            if (null == hostnameVerifier) {
                x509TrustManager = new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        // ignore
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        // ignore
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[]{};
                    }
                };

                final SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, new TrustManager[]{x509TrustManager}, new java.security.SecureRandom());

                // Create an ssl socket factory with our all-trusting manager
                sslSocketFactory = sslContext.getSocketFactory();

                hostnameVerifier = new HostnameVerifier() {
                    @Override
                    public boolean verify(String s, SSLSession sslSession) {
                        return true;
                    }
                };
            }
        }
    }
}
