package com.wangjie.dal.request.library;


import com.dangbei.xlog.XLog;
import com.wangjie.dal.request.library.ssl.XHttpSSLBuilder;
import com.wangjie.dal.request.library.ssl.XUnsafeHttpSSLBuilder;

import java.util.Arrays;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;

/**
 * Author: wangjie Email: tiantian.china.2@gmail.com Date: 7/6/15.
 */
public final class XHttpClient {

    private static final String TAG = XHttpClient.class.getSimpleName();
    private static XHttpSSLBuilder xHttpSSLBuilder = new XUnsafeHttpSSLBuilder();
//    private static XHttpSSLBuilder xHttpSSLBuilder = new XSafeHttpSSLBuilder();

    private static OkHttpClient okHttpClient = getOkHttpClient();

    private XHttpClient() {
    }

    public static OkHttpClient get() {
        return okHttpClient;
    }

    public static OkHttpClient create() {
        return getOkHttpClient();
    }

    private static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient().newBuilder();
        try {
            okHttpClientBuilder.sslSocketFactory(xHttpSSLBuilder.getSslSocketFactory(), xHttpSSLBuilder.getX509TrustManager());
            okHttpClientBuilder.protocols(Arrays.asList(Protocol.HTTP_1_1));
            okHttpClientBuilder.hostnameVerifier(xHttpSSLBuilder.getHostnameVerifier());
        } catch (Exception e) {
            XLog.e(TAG, e);
        }
        return okHttpClientBuilder.build();
    }

    public static OkHttpClient.Builder createOkHttpClientBuilder() {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient().newBuilder();
        try {
            okHttpClientBuilder.sslSocketFactory(xHttpSSLBuilder.getSslSocketFactory(), xHttpSSLBuilder.getX509TrustManager());
            okHttpClientBuilder.protocols(Arrays.asList(Protocol.HTTP_1_1));
            okHttpClientBuilder.hostnameVerifier(xHttpSSLBuilder.getHostnameVerifier());
        } catch (Exception e) {
            XLog.e(TAG, e);
        }
        return okHttpClientBuilder;
    }

//    private static OkHttpClient getUnsafeOkHttpClient() {
//        try {
//            X509TrustManager x509TrustManager = new X509TrustManager() {
//                @Override
//                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
//                }
//
//                @Override
//                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
//                }
//
//                @Override
//                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//                    return new X509Certificate[]{};
//                }
//            };
//
//            // Install the all-trusting trust manager
//            final SSLContext sslContext = SSLContext.getInstance("TLS");
//            sslContext.init(null, new TrustManager[]{x509TrustManager}, new java.security.SecureRandom());
//
//            // Create an ssl socket factory with our all-trusting manager
//            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
//
//            OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient().newBuilder();
//            okHttpClientBuilder.sslSocketFactory(sslSocketFactory, x509TrustManager);
//            okHttpClientBuilder.protocols(Arrays.asList(Protocol.HTTP_1_1));
//            okHttpClientBuilder.hostnameVerifier((hostname, session) -> true);
//
//            return okHttpClientBuilder.build();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

//    private static OkHttpClient getSafeOkHttpClient() {
//        try {
//            InputStream inputStream = ProviderApplication.getInstance().getApplication().getAssets().open("achelous.pem");
//
//            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
//
//            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
//            keyStore.load(null);
//
//            String certificateAlias = "achelous";
//            keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(inputStream));
//
//            SSLContext sslContext = SSLContext.getInstance("TLS");
//            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//            trustManagerFactory.init(keyStore);
//            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
//
//            X509TrustManager x509TrustManager = new X509TrustManager() {
//                @Override
//                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
//                }
//
//                @Override
//                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
//                }
//
//                @Override
//                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//                    return new X509Certificate[]{};
//                }
//            };
//
//            // Create an ssl socket factory with our all-trusting manager
//            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
//
//            OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient().newBuilder();
//            okHttpClientBuilder.sslSocketFactory(sslSocketFactory, x509TrustManager);
//            okHttpClientBuilder.protocols(Arrays.asList(Protocol.HTTP_1_1));
//            okHttpClientBuilder.hostnameVerifier((hostname, session) -> hostname.endsWith("dangcdn.com"));
//
//            return okHttpClientBuilder.build();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

}
