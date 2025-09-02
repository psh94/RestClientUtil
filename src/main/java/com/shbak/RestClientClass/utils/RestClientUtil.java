package com.shbak.RestClientClass.utils;


import com.shbak.RestClientClass.dto.SslMode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import static com.shbak.RestClientClass.dto.SslMode.*;

public class RestClientUtil {


    private final RestClient safeClient;    // SSL 적용
    private final RestClient unsafeClient;  // SSL 무시

    public RestClientUtil() {
        try{
            this.safeClient = RestClient.builder().build();
            this.unsafeClient = UnsafeRestClientFactory.create();
        } catch(Exception e){
            throw new RuntimeException("Init RestClientUtil failed", e);
        }
    }

    private RestClient pick(SslMode mode) {
        return (mode == IGNORE) ? unsafeClient : safeClient;
    }

    // -----------------------------------------------------------------------------------------------
    public <T> T get(String url, Class<T> responseType, SslMode mode){
        return pick(mode).get()         // 선택된 RestClient로 get메서드 선택
                .uri(url)               // url 주소 입력
                .retrieve()             // 해당 uri로 http 요청
                .body(responseType);    // .body(): HttpMessage의 body만 반환
    }

    public String get(String url){
        return get(url, String.class, DEFAULT);
    }

    public ResponseEntity<String> getEntity(String url, SslMode mode){
        return pick(mode).get()
                .uri(url)
                .retrieve()
                .toEntity(String.class);    // .toEntity: HttpMessage 전체를 반환
    }
    // -----------------------------------------------------------------------------------------------
    public <T> T post(String url, Object body, Class<T> responseType, SslMode mode){
        return pick(mode).post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .body(body)
                .retrieve()
                .body(responseType);
    }

    public <T> T post(String url, Object body, Class<T> responseType) {
        return post(url, body, responseType, DEFAULT);
    }

}
