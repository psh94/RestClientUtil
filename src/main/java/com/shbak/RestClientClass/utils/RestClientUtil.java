package com.shbak.RestClientClass.utils;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

public class RestClientUtil {

    public enum SslMode { DEFAULT, IGNORE }

    private final RestClient safeClient;
    private final RestClient unsafeClient;

    public RestClientUtil() {
        try{
            this.safeClient = RestClient.builder().build();
            this.unsafeClient = UnsafeRestClientFactory.create();
        } catch(Exception e){
            throw new RuntimeException("Init RestClientUtil failed", e);
        }
    }

    private RestClient pick(SslMode mode) {
        return (mode == SslMode.IGNORE) ? unsafeClient : safeClient;
    }

    // -----------------------------------------------------------------------------------------------
    public <T> T get(String url, Class<T> responseType, SslMode mode){
        return pick(mode).get().uri(url).retrieve().body(responseType);
    }

    public String get(String url){
        return get(url, String.class, SslMode.DEFAULT);
    }

    public ResponseEntity<String> getEntity(String url, SslMode mode){
        return pick(mode).get().uri(url).retrieve().toEntity(String.class);
    }

    // -----------------------------------------------------------------------------------------------
    public <T> T post(String url, Object body, Class<T> responseType, SslMode mode){
        return pick(mode).post().uri(url).contentType(MediaType.APPLICATION_JSON).body(body).retrieve().body(responseType);
    }

    public <T> T post(String url, Object body, Class<T> responseType) {
        return post(url, body, responseType, SslMode.DEFAULT);
    }

}
