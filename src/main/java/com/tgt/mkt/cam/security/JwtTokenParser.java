package com.tgt.mkt.cam.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;

/**
 * Created by z083387 on 3/5/18.
 */
@Slf4j
@Component
public class JwtTokenParser implements Serializable {

    public static final String USER_EMAIL_ID = "mail";
    public static final String USER_NAME = "samaccountname";
    public static final String USER_FIRST_NAME = "firstname";
    public static final String USER_LAST_NAME = "lastname";
    public static final String DEFAULT_ROLE = "CGI_READ_ONLY_ROLE";
    /**
     * Tries to parse specified String as a JWT token. If successful, returns User object with username, id and role
     * prefilled (extracted from token).
     * If unsuccessful (token is invalid or not containing all required user properties), simply returns null.
     *
     * @param token the JWT token to parse
     * @return the User object extracted from specified token or null if a token is invalid.
     */
    public JwtToken parseToken(String token) {
        JwtToken jwtToken = new JwtToken();
        JWT jwt = null;
        try {
            jwt = JWT.decode(token);
            if (!ObjectUtils.isEmpty(jwt.getClaim(USER_EMAIL_ID))) {
                jwtToken.setEmail(jwt.getClaim(USER_EMAIL_ID).asString());
            }
            if (!ObjectUtils.isEmpty(jwt.getClaim(USER_NAME))) {
                jwtToken.setUsername(jwt.getClaim(USER_NAME).asString());
            }
            if (!ObjectUtils.isEmpty(jwt.getClaim(USER_FIRST_NAME))) {
                jwtToken.setFirstName(jwt.getClaim(USER_FIRST_NAME).asString());
            }
            if (!ObjectUtils.isEmpty(jwt.getClaim(USER_LAST_NAME))) {
                jwtToken.setLastName(jwt.getClaim(USER_LAST_NAME).asString());
            }
            jwtToken.setRole(DEFAULT_ROLE);
        } catch (JWTDecodeException exception) {
            log.error("Error while parsing JWT token ", exception);
        }
        return jwtToken;
    }
}
