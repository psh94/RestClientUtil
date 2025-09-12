package com.shbak.RestClientClass.utils;

import com.shbak.RestClientClass.config.RestClientConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.ResourceAccessException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
        (classes = {RestClientConfig.class},
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
class RestClientUtilTest {

    @Autowired
    RestClientUtil util;

    @Test
    @DisplayName("GET 성공 -> ignoreSsl=true (unsafe 사용)")
    void test1() {
        ReflectionTestUtils.setField(util, "ignoreSsl", true);
        String url = "https://www.mxfleet.net/index.html"; // 정상 인증된 HTTPS
        ResponseEntity<String> res = util.getEntity(url);

        // 출력 (요청 결과와 상태)
        System.out.println("[GET REQUEST] : " + url);
        System.out.println("[STATUS] : " + res.getStatusCode());
        System.out.println("[BODY]   : " + res.getBody());

        assertTrue(res.getStatusCode().is2xxSuccessful());
    }

    @Test
    @DisplayName("GET 요청 실패 테스트 -> ignoreSsl: false")
    void test2() {
        ReflectionTestUtils.setField(util, "ignoreSsl", false);
        String url = "https://www.mxfleet.net/index.html"; // 정상 인증된 HTTPS

        RuntimeException error = assertThrows(ResourceAccessException.class, () -> util.get(url));
        System.out.println("[ERROR] : " + error.getMessage());
    }
}
