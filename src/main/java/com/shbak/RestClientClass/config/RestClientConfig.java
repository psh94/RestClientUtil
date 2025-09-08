package com.shbak.RestClientClass.config;

import com.shbak.RestClientClass.utils.RestClientUtil;
import com.shbak.RestClientClass.utils.UnsafeRestClientFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Value("${app.ssl.ignore:false}")   // application.yml / properties 에서 제어
    private boolean ignoreSsl;

    @Bean
    public RestClient safeRestClient(){
        return RestClient.builder().build();
    }

    @Bean
    public RestClient unsafeRestClient(){
        return UnsafeRestClientFactory.create();
    }

    @Bean
    public RestClientUtil restClientUtil(RestClient safeRestClient, RestClient unsafeRestClient) {
        return new RestClientUtil(safeRestClient, unsafeRestClient, ignoreSsl);
    }
}
