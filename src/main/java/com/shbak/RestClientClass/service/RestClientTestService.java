package com.shbak.RestClientClass.service;

import com.shbak.RestClientClass.dto.PostRequest;
import com.shbak.RestClientClass.utils.RestClientUtil;
import org.springframework.stereotype.Service;

import static com.shbak.RestClientClass.dto.SslMode.IGNORE;

@Service
public class RestClientTestService {

    RestClientUtil restClientUtil = new RestClientUtil();

    public String testRestClientGetReq() {
        try{
            // SSL 무시
            String result = restClientUtil.get("https://www.mxfleet.net/index.html", String.class, IGNORE);
            System.out.println("result: " + result);

            // SSL 적용
            // String result = restClientUtil.get("https://www.mxfleet.net/index.html");
            // System.out.println("result: " + result);

            return result;
        } catch (Exception e) {
            throw new RuntimeException("SSL Ignorance failed");
        }
    }

    public String testRestclientPostReq() {
        var req = new PostRequest("post request test body: abcdefg...");
        try{
            // SSL 무시
            String result = restClientUtil.post("https://postman-echo.com/post", req, String.class, IGNORE);
            System.out.println("result : " + result);

            // SSL 적용
            // String result = restClientUtil.post("https://postman-echo.com/post", req, String.class);
            // System.out.println("result : " + result);

            return result;
        } catch (Exception e) {
            throw new RuntimeException("SSL Ignorance failed");
        }

    }
}
