package com.tgt.mkt.cam.security;

import com.tgt.mkt.cam.exception.JwtTokenMalformedException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao
        .AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by Saugata.Dutta on 05/06/18.
 */

@Component
@Slf4j
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    @Autowired
    private JwtTokenParser jwtTokenParser;

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
            UsernamePasswordAuthenticationToken
                    authentication) throws
            AuthenticationException {
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken
            authentication) throws
            AuthenticationException {
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken)
                authentication;
        String token = jwtAuthenticationToken.getToken();
        JwtToken parsedUser = null;
        try {
            parsedUser = jwtTokenParser.parseToken(token);
            if (null == parsedUser) {
                throw new MalformedJwtException("JWT token is not valid");
            }
        } catch (UnsupportedEncodingException | ExpiredJwtException | UnsupportedJwtException |
                MalformedJwtException
                | SignatureException | IllegalArgumentException | JwtTokenMalformedException
                ex) {
            log.error("Error while parsing JWT token : {}",
                    ex.getMessage());
            throw new JwtTokenMalformedException("JWT token is not valid");
        }

        List<GrantedAuthority> authorityList = AuthorityUtils
                .commaSeparatedStringToAuthorityList
                (null);

        return new AuthenticatedUser(parsedUser.getDocumentNumber(),
                parsedUser.getEmail(),
                parsedUser.getIssuer(),
                authorityList);
    }

}
