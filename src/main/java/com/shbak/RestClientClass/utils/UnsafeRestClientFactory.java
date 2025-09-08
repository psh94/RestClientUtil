package com.shbak.RestClientClass.utils;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.ClientTlsStrategyBuilder;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.apache.hc.core5.ssl.TrustStrategy;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import javax.net.ssl.SSLContext;
import java.security.cert.X509Certificate;

public class UnsafeRestClientFactory {

    public static RestClient create() {
        try{
            TrustStrategy trustAll = (X509Certificate[] chain, String authType) -> true; // 모든 인증서를 무조건 신뢰(true 반환)

            SSLContext sslContext = SSLContextBuilder.create()
                    .loadTrustMaterial(null, trustAll)
                    .build();

            var tlsStrategy = ClientTlsStrategyBuilder.create()
                    .setSslContext(sslContext)
                    .setHostnameVerifier(NoopHostnameVerifier.INSTANCE) // NoopHostnameVerifier: 모든 호스트 명을 무조건 신뢰
                    .buildClassic();

            var connManager = PoolingHttpClientConnectionManagerBuilder.create()
                    .setTlsSocketStrategy(tlsStrategy)
                    .build();

            CloseableHttpClient httpClient = HttpClients.custom()
                    .setConnectionManager(connManager)
                    .build();

            var requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
            return RestClient.builder()
                    .requestFactory(requestFactory)
                    .build();
        } catch(Exception e) {
            throw new RuntimeException("create unsafeRestClientFactory failed");
        }
    }
}
