package com.shbak.RestClientClass.controller;

import com.shbak.RestClientClass.dto.PostRequest;
import com.shbak.RestClientClass.utils.RestClientUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.shbak.RestClientClass.utils.RestClientUtil.SslMode.*;

@RestController
public class RestClientTestController {

    RestClientUtil restClientUtil = new RestClientUtil();

    @GetMapping("/test/restclient")
    public String testRestClient() {
        try{
            // SSL 무시
            String sslIgnoreMode = restClientUtil.get("https://www.mxfleet.net/index.html", String.class, IGNORE);
            System.out.println("sslIgnoreMode: " + sslIgnoreMode);

            // SSL 적용
            // String sslDefaultMode = restClientUtil.get("https://www.mxfleet.net/index.html");
            // System.out.println("sslDefaultMode: " + sslDefaultMode);
            return sslIgnoreMode;

        } catch (Exception e) {
            throw new RuntimeException("SSL Ignorance failed");
        }
    }

    @GetMapping("/test/restclient/post")
    public String postToHttpbin() {
        var req = new PostRequest("hello", "world", 99);
        try{
            // SSL 무시
            String ignoreResult = restClientUtil.post("https://postman-echo.com/post", req, String.class, IGNORE);
            System.out.println("ignoreResult : " + ignoreResult);
            // SSL 적용
            String defaultResult = restClientUtil.post("https://postman-echo.com/post", req, String.class);
            System.out.println("defaultResult : " + defaultResult);

            return ignoreResult;
        } catch (Exception e) {
            throw new RuntimeException("SSL Ignorance failed");
        }

    }
}
