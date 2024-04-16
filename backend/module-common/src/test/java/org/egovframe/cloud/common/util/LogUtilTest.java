package org.egovframe.cloud.common.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

class LogUtilTest {

    @Test
    void testGetUserIp() {
        // Create a mock request
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRemoteAddr("192.168.1.1"); // Fallback IP

        // Set various headers to simulate different scenarios
        request.addHeader("Proxy-Client-IP", "192.168.1.2");
        request.addHeader("WL-Proxy-Client-IP", "192.168.1.2");
        request.addHeader("HTTP_CLIENT_IP", "192.168.1.2");
        request.addHeader("HTTP_X_FORWARDED_FOR", "192.168.1.2");
        request.addHeader("X-Real-IP", "192.168.1.2");
        request.addHeader("X-RealIP", "192.168.1.2");
        request.addHeader("REMOTE_ADDR", "192.168.1.2");
        request.addHeader("X-Forwarded-For", "192.168.1.2");

        // Set the request attributes for the current thread
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        // Call the method under test
        String ip = LogUtil.getUserIp();

        // Assert the expected IP address
        assertEquals("192.168.1.2", ip); // Expecting the first non-empty and non-"unknown" header value

        // Reset the request attributes to avoid affecting other tests
        RequestContextHolder.resetRequestAttributes();
    }
}
