package com.tgt.mkt.cam.security;

import com.tgt.mkt.cam.exception.JwtTokenMalformedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import static com.tgt.mkt.cam.security.GenerateToken.DOCUMENT_NUMBER;
import static com.tgt.mkt.cam.security.GenerateToken.E_MAIL;
import static com.tgt.mkt.cam.security.GenerateToken.JTI_FIELD;

/**
 * Created by Saugata.Dutta on 05/06/18.
 */
@Slf4j
@Component
public class JwtTokenParser implements Serializable {

    @Value(value = "${jwt.jti}")
    private String JTI_VALUE;
    @Value(value = "${jwt.secret}")
    private String SECRET;

    @Value(value = "${jwt.expiry}")
    private int EXPIRY;


    /**
     * Tries to parse specified String as a JWT token. If successful, returns User object with
     * username, id and role
     * prefilled (extracted from token).
     * If unsuccessful (token is invalid or not containing all required user properties),
     * simply
     * returns null.
     *
     * @param token the JWT token to parse
     * @return the User object extracted from specified token or null if a token is invalid.
     */
    public JwtToken parseToken(String token) throws UnsupportedEncodingException {
        JwtToken jwtToken = null;

        Claims claims = Jwts.parser().setSigningKey(SECRET.getBytes("UTF-8")).parseClaimsJws
                (token)
                .getBody();
        jwtToken = new JwtToken();
        if (ObjectUtils.isEmpty(claims.getExpiration())) {
            throw new JwtTokenMalformedException("No Expiration");
        }
        if (claims.getExpiration().getTime() - claims.getIssuedAt()
                .getTime() > EXPIRY) {
            throw new JwtTokenMalformedException("Expiration more than 2 hrs");
        }
        if (ObjectUtils.isEmpty(claims.get(JTI_FIELD)) || !JTI_VALUE.equalsIgnoreCase(claims
                .get(JTI_FIELD).toString())) {
            throw new JwtTokenMalformedException("Invalid JTI");
        }
        if (!ObjectUtils.isEmpty(claims.get(E_MAIL))) {
            jwtToken.setEmail(claims.get(E_MAIL).toString());
        }
        if (!ObjectUtils.isEmpty(claims.get(DOCUMENT_NUMBER))) {
            jwtToken.setDocumentNumber(claims.get(DOCUMENT_NUMBER).toString());
        }
        if (!ObjectUtils.isEmpty(claims.getIssuer())) {
            jwtToken.setIssuer(claims.getIssuer());
        }
        return jwtToken;
    }
}
