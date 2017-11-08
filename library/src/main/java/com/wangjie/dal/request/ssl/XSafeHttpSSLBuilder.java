package com.wangjie.dal.request.ssl;

/**
 * Author: wangjie Email: tiantian.china.2@gmail.com Date: 12/7/16.
 */
public class XSafeHttpSSLBuilder extends XHttpSSLBuilder {
    @Override
    protected void ensureSSLBuilder() throws Exception {
//        if (null != hostnameVerifier) {
//            return;
//        }
//        synchronized (this) {
//            if (null == hostnameVerifier) {
//                InputStream inputStream = ProviderApplication.getInstance().getApplication().getAssets().open("achelous.pem");
//
//                CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
//
//                KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
//                keyStore.load(null);
//
//                String certificateAlias = "achelous";
//                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(inputStream));
//
//                SSLContext sslContext = SSLContext.getInstance("TLS");
//                TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//                trustManagerFactory.init(keyStore);
//                sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
//
//                x509TrustManager = new X509TrustManager() {
//                    @Override
//                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//                        // ignore
//                    }
//
//                    @Override
//                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//                        // ignore
//                    }
//
//                    @Override
//                    public X509Certificate[] getAcceptedIssuers() {
//                        return new X509Certificate[]{};
//                    }
//                };
//
//                // Create an ssl socket factory with our all-trusting manager
//                sslSocketFactory = sslContext.getSocketFactory();
//                hostnameVerifier = new HostnameVerifier() {
//                    @Override
//                    public boolean verify(String hostname, SSLSession sslSession) {
//                        return hostname.endsWith("dangcdn.com");
//                    }
//                };
//
//            }
//        }
    }
}
