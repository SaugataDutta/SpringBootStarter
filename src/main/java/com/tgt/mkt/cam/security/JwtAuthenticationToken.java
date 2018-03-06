package com.tgt.mkt.cam.security;

import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * Created by z083387 on 3/5/18.
 */

@Getter
public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {
    private String token;

    public JwtAuthenticationToken(String token) {
        super(null, null);
        this.token = token;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
