package com.shbak.RestClientClass.utils;

import lombok.Getter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

public class RestClientUtil {

    private final RestClient safeClient;    // SSL O
    private final RestClient unsafeClient;  // SSL X (무시)
    private final boolean ignoreSsl;        // SSL 무시 여부

    public RestClientUtil(RestClient safeClient, RestClient unsafeClient, boolean ignoreSsl) {
        this.safeClient = safeClient;
        this.unsafeClient = unsafeClient;
        this.ignoreSsl = ignoreSsl;
    }

    private RestClient pick() {
        return ignoreSsl ? unsafeClient : safeClient;
    }

    // -----------------------------------------------------------------------------------------------
    public <T> T get(String url, Class<T> responseType){
        return pick().get()             // 선택된 RestClient로 get메서드 선택
                .uri(url)               // url 주소 입력
                .retrieve()             // 해당 uri로 http 요청
                .body(responseType);    // .body(): HttpMessage의 body만 반환
    }

    public String get(String url){
        return get(url, String.class);
    }

    public ResponseEntity<String> getEntity(String url){
        return pick().get()
                .uri(url)
                .retrieve()
                .toEntity(String.class);    // .toEntity: HttpMessage 전체를 반환
    }
    // -----------------------------------------------------------------------------------------------
    public <T> T post(String url, Object body, Class<T> responseType){
        return pick().post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .body(body)
                .retrieve()
                .body(responseType);
    }

    public ResponseEntity<String> post(String url, Object body){
        return pick().post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .body(body)
                .retrieve()
                .toEntity(String.class);
    }

    // -----------------------------------------------------------------------------------------------
    public <T> T patch(String url, Object body, Class<T> responseType){
        return pick().patch()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .body(body)
                .retrieve()
                .body(responseType);
    }

    public ResponseEntity<String> patch(String url, Object body){
        return pick().patch()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .body(body)
                .retrieve()
                .toEntity(String.class);
    }
    // -----------------------------------------------------------------------------------------------
    public <T> T delete(String url, Class<T> responseType){
        return pick().delete()
                .uri(url)
                .retrieve()
                .body(responseType);
    }

    public ResponseEntity<String> delete(String url){
        return pick().delete()
                .uri(url)
                .retrieve()
                .toEntity(String.class);
    }
}
