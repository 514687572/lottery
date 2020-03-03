package com.stip.net.oauth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * Returns a 401 error code (Unauthorized) to the client.
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final Logger log = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);

    public void commence(HttpServletRequest request,HttpServletResponse response,AuthenticationException ae) throws IOException, ServletException {
        log.error("访问被拒绝，请联系管理员。Access Denied");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "访问被拒绝，请联系管理员。Access Denied");
    }
}