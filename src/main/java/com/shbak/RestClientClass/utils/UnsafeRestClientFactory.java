package com.shbak.RestClientClass.utils;

import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.http.HttpClient;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class UnsafeRestClientFactory {

    public static RestClient create() {
        try{
            // 모든 인증서를 신뢰
            TrustManager[] trustAll = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
                        @Override
                        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
                        @Override
                        public X509Certificate[] getAcceptedIssuers() {return new X509Certificate[0];}
                    }
            };

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAll, new SecureRandom());

            SSLParameters sslParams = new SSLParameters();
            sslParams.setEndpointIdentificationAlgorithm(null); // 호스트네임 검증 비활성화

            HttpClient httpClient = HttpClient.newBuilder()
                    .sslContext(sslContext)
                    .sslParameters(sslParams)
                    .build();

            var requestFactory = new JdkClientHttpRequestFactory(httpClient);
            return RestClient.builder()
                    .requestFactory(requestFactory)
                    .build();
        } catch(Exception e) {
            throw new RuntimeException("create unsafeRestClientFactory failed");
        }
    }
}
