package com.tgt.mkt.cam.security;

import com.tgt.mkt.cam.security.exception.JwtTokenMalformedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by z083387 on 3/5/18.
 */

@Component
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    @Autowired
    private JwtTokenParser jwtTokenParser;

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken
            authentication) throws AuthenticationException {
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws
            AuthenticationException {
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        String token = jwtAuthenticationToken.getToken();
        JwtToken parsedUser = jwtTokenParser.parseToken(token);

        if (parsedUser == null) {
            throw new JwtTokenMalformedException("JWT token is not valid");
        }

        List<GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList
                (parsedUser.getRole());

        return new AuthenticatedUser(parsedUser.getUsername(), parsedUser.getEmail(), parsedUser
                .getFirstName(),parsedUser.getLastName(),
                authorityList);
    }

}
