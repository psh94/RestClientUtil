//package com.shbak.RestClientClass.controller;
//
//import com.shbak.RestClientClass.service.RestClientTestService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequiredArgsConstructor
//public class RestClientTestController {
//
//    private final RestClientTestService restClientTestService;
//
//    @GetMapping("/test/restclient")
//    public String testRestClientGetReq() {
//        return restClientTestService.testRestClientGetReq();
//    }
//
//    @GetMapping("/test/restclientpost")
//    public String testRestclientPostReq() {
//        return restClientTestService.testRestclientPostReq();
//    }
//
//    @GetMapping("/test/restclientpatch")
//    public String testRestclientPatchReq() {
//        return restClientTestService.testRestclientPatchReq();
//    }
//
//    @GetMapping("/test/restclientdelete")
//    public String testRestclientDeleteReq() {
//        return restClientTestService.testRestclientDeleteReq();
//    }
//}
