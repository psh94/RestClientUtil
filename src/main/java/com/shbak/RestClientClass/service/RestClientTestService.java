//package com.shbak.RestClientClass.service;
//
//import com.shbak.RestClientClass.dto.PostRequest;
//import com.shbak.RestClientClass.utils.RestClientUtil;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class RestClientTestService {
//
//    private final RestClientUtil restClientUtil;
//
//    public String testRestClientGetReq() {
//        try{
//            return restClientUtil.get("https://www.mxfleet.net/index.html", String.class);
//        } catch (Exception e) {
//            throw new RuntimeException("get request test failed");
//        }
//    }
//
//    public String testRestclientPostReq() {
//        var req = new PostRequest("post request test body: post post ...");
//        try{
//            return restClientUtil.post("https://httpbin.org/post", req, String.class);
//        } catch (Exception e) {
//            throw new RuntimeException("post request test failed");
//        }
//    }
//
//    public String testRestclientPatchReq() {
//        var req = new PostRequest("patch request test body: patch patch ...");
//        try{
//            return restClientUtil.patch("https://httpbin.org/patch", req, String.class);
//        } catch (Exception e) {
//            throw new RuntimeException("patch request test failed");
//        }
//    }
//
//    public String testRestclientDeleteReq() {
//        try{
//            return restClientUtil.delete("https://httpbin.org/delete", String.class);
//        } catch (Exception e) {
//            throw new RuntimeException("delete request test failed");
//        }
//    }
//}
