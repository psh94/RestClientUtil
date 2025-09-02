package com.shbak.RestClientClass.utils;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactoryBuilder;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.apache.hc.core5.ssl.TrustStrategy;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import javax.net.ssl.SSLContext;
import java.security.cert.X509Certificate;

public class UnsafeRestClientFactory {

    public static RestClient create() throws Exception {
        TrustStrategy trustAll = (X509Certificate[] chain, String authType) -> true;

        SSLContext sslContext = SSLContextBuilder.create()
                .loadTrustMaterial(null, trustAll)
                .build();

        SSLConnectionSocketFactory sslSocketFactory =
                SSLConnectionSocketFactoryBuilder.create()
                        .setSslContext(sslContext)
                        .setHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                        .build();

        var connManager = PoolingHttpClientConnectionManagerBuilder.create()
                .setSSLSocketFactory(sslSocketFactory)
                .build();

        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(connManager)
                .build();

        var requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        return RestClient.builder()
                .requestFactory(requestFactory)
                .build();
    }
}
