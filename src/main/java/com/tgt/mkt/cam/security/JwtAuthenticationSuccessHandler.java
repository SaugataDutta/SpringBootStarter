package com.tgt.mkt.cam.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication
            authentication) {
        // Do do anything specific here
        log.info("JwtAuthenticationSuccessHandler.onAuthenticationSuccess : Authentication " +
                authentication
                .getCredentials());
    }

}