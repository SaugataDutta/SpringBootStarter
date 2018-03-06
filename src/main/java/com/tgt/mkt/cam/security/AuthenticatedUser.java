package com.tgt.mkt.cam.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Created by z083387 on 3/5/18.
 */
@AllArgsConstructor
public class AuthenticatedUser implements UserDetails {

    private String username;
    @Getter
    private String email;
    @Getter
    private String firstName;
    @Getter
    private String lastName;

    private Collection<? extends GrantedAuthority> authorities;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
