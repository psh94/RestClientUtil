package com.shbak.RestClientClass.config;

import com.shbak.RestClientClass.utils.RestClientUtil;
import com.shbak.RestClientClass.utils.UnsafeRestClientFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Value("${app.ssl.ignore:false}")   // application.properties 에서 제어, default값 false
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
    public RestClientUtil restClientUtil(@Qualifier("safeRestClient") RestClient safeRestClient,
                                         @Qualifier("unsafeRestClient") RestClient unsafeRestClient) {
        return new RestClientUtil(safeRestClient, unsafeRestClient, ignoreSsl);
    }
}
