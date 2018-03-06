package com.tgt.mkt.cam.security;

import com.tgt.mkt.cam.security.exception.JwtTokenMalformedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by z083387 on 3/5/18.
 */
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    @Value(value = "${jwt.header}")
    private String HEADER_STRING;

    @Value(value = "${token.prefix}")
    private String TOKEN_PREFIX;


    public JwtAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse
            response) {
        String header = request.getHeader(HEADER_STRING);
        String authToken = null;

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            throw new JwtTokenMalformedException("No JWT token found in request headers");
        }
        authToken = header.replace(TOKEN_PREFIX, "");
        JwtAuthenticationToken authRequest = new JwtAuthenticationToken(authToken);
        return getAuthenticationManager().authenticate(authRequest);

    }
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain
            chain, Authentication authResult)
            throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);

        // As this authentication is in HTTP header, after success we need to continue the request normally
        // and return the response as if the resource was not secured at all
        chain.doFilter(request, response);
    }

}
