package com.shbak.RestClientClass.utils;

import com.shbak.RestClientClass.config.RestClientConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
        (classes = {RestClientConfig.class},
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
class RestClientUtilTest {

    @Autowired
    RestClientUtil util;

    @Test
    @DisplayName("GET 성공 -> ignoreSsl=true (unsafe 사용)")
    void get_success_when_ignoreSsl_true() {
        ReflectionTestUtils.setField(util, "ignoreSsl", true);
        String url = "https://www.mxfleet.net/index.html";
//        String url = "https://self-signed.badssl.com/"; // SSL 테스트용 사이트
        ResponseEntity<String> res = util.getEntity(url);

        // 출력 (요청 결과와 상태)
        System.out.println("[URL] : " + url);
        System.out.println("[STATUS] : " + res.getStatusCode());
        System.out.println("[BODY]   : " + res.getBody());

        assertTrue(res.getStatusCode().is2xxSuccessful());
    }

    @Test
    @DisplayName("GET 요청 실패 테스트 -> ignoreSsl: false")
    void get_fail_when_ignoreSsl_false() {
        ReflectionTestUtils.setField(util, "ignoreSsl", false);
        String url = "https://www.mxfleet.net/index.html";
//        String url = "https://self-signed.badssl.com/"; // SSL 테스트용 사이트

        RuntimeException error = assertThrows(RuntimeException.class, () -> util.get(url));
        System.out.println("[ERROR] : " + error.getMessage());
    }

    /**
     * POST, PATCH, DELETE 테스트
     * - SSL 무시 여부를 확인할 수 있는 공개 테스트 사이트가 마땅히 없다고 판단함
     * - 따라서 ignoreSsl=true로 고정하여 정상 호출 여부만 테스트 함
     */
    @Test
    @DisplayName("POST 성공 테스트")
    void post_success() {
        ReflectionTestUtils.setField(util, "ignoreSsl", true);
        String url = "https://httpbin.org/post"; // SSL 테스트용 사이트
        ResponseEntity<String> res = util.post(url, "post request test body: post post ...");

        // 출력 (요청 결과와 상태)
        System.out.println("[URL] : " + url);
        System.out.println("[STATUS] : " + res.getStatusCode());
        System.out.println("[BODY]   : " + res.getBody());

        assertTrue(res.getStatusCode().is2xxSuccessful());
    }

    @Test
    @DisplayName("PATCH 성공 테스트")
    void patch_success() {
        ReflectionTestUtils.setField(util, "ignoreSsl", true);
        String url = "https://httpbin.org/patch"; // SSL 테스트용 사이트
        ResponseEntity<String> res = util.patch(url, "post request test body: post post ...");

        // 출력 (요청 결과와 상태)
        System.out.println("[URL] : " + url);
        System.out.println("[STATUS] : " + res.getStatusCode());
        System.out.println("[BODY]   : " + res.getBody());

        assertTrue(res.getStatusCode().is2xxSuccessful());
    }

    @Test
    @DisplayName("DELETE 성공 테스트")
    void delete_success() {
        ReflectionTestUtils.setField(util, "ignoreSsl", true);
        String url = "https://httpbin.org/delete"; // SSL 테스트용 사이트
        ResponseEntity<String> res = util.delete(url);

        // 출력 (요청 결과와 상태)
        System.out.println("[URL] : " + url);
        System.out.println("[STATUS] : " + res.getStatusCode());
        System.out.println("[BODY]   : " + res.getBody());

        assertTrue(res.getStatusCode().is2xxSuccessful());
    }

}
