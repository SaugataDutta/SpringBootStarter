package com.tgt.mkt.cam.security;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang.time.DateUtils;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.util.Date;

/**
 * Created by Saugata.Dutta on 05/06/18.
 */

public class GenerateToken {

    public static final String JTI_VALUE = "69171853-0314-40fa-9dbd-91e89ef3c952";

    //public static final String SECRET = "y9SXv0FsOhrHxZnTVlJt";

    public static final String SECRET = "secret";

    /* Issuer will be based on business - FACL(Falabella-Chile), SOCL(Sodimac-Chile), TOCL
    (Tottus-Chile) */
    public static final String FALABELLA_ISSUER = "TOCL";

    public static final String JWT_TOKEN = "JwtToken";

    public static final String SUBJECT = "oAUth2";

    public static final String E_MAIL = "email";

    public static final String DOCUMENT_NUMBER = "documentNumber";

    public static final int TOKEN_EXPIRY = 2;

    public static final String JTI_FIELD = "jti";

    public static String createJWT(ProfileToken profileToken) throws UnsupportedEncodingException {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        byte[] apiKeySecretBytes = SECRET.getBytes("UTF-8");
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        JwtBuilder builder = Jwts.builder().setId(JWT_TOKEN).setIssuedAt(now).setSubject(SUBJECT)
                .setIssuer(FALABELLA_ISSUER).signWith(signatureAlgorithm, signingKey).claim
                        (JTI_FIELD, JTI_VALUE)
                .claim(E_MAIL, profileToken.getEmail()).claim(DOCUMENT_NUMBER, profileToken
                        .getDocumentNumber());
        builder.setExpiration(DateUtils.addHours(now, TOKEN_EXPIRY));
        return builder.compact();
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        ProfileToken profile = new ProfileToken("8304218-4", "sdutta@falabella.cl");
        String token = createJWT(profile);
        System.out.println(token);
    }
}
