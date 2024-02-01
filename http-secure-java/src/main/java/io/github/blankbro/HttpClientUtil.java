package io.github.blankbro;

import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

@Slf4j
public class HttpClientUtil {

    public static CloseableHttpClient defaultClient() {
        return HttpClients.createDefault();
    }

    public static CloseableHttpClient customHttpClient() throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException, KeyManagementException {
        // SSLContext sslContext = SSLContextBuilder
        //         .create()
        //         .loadTrustMaterial(new TrustStrategy() {
        //             @Override
        //             public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
        //                 for (X509Certificate x509Certificate : x509Certificates) {
        //                     log.info("{}", x509Certificate.getSubjectDN().toString());
        //                 }
        //                 return false;
        //             }
        //         })
        //         .build();

        SSLContext sslContext = SSLContext.getInstance("SSL");
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws CertificateException {
                for (X509Certificate x509Certificate : x509Certificates) {
                    log.info("checkClientTrusted {}", x509Certificate.getSubjectDN().toString());
                }
            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws CertificateException {
                for (X509Certificate x509Certificate : x509Certificates) {
                    x509Certificate.getPublicKey().getFormat();
                    log.info("checkServerTrusted {}", x509Certificate.getSubjectDN().toString());
                }
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        }};
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());


        SSLConnectionSocketFactory factory = new SSLConnectionSocketFactory(sslContext);
        PoolingHttpClientConnectionManager connectionManager = PoolingHttpClientConnectionManagerBuilder
                .create()
                .setSSLSocketFactory(factory)
                .build();

        CloseableHttpClient httpClient = HttpClients
                .custom()
                .setConnectionManager(connectionManager)
                .build();
        return httpClient;
    }

}
