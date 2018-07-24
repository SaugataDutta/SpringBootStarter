package com.tgt.mkt.cam.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration
        .EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration
        .WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Arrays;

/**
 * Created by Saugata.Dutta on 05/06/18.
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String PATH = "/profile/**";

    @Autowired
    private JwtAuthenticationProvider authenticationProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {

        return new ProviderManager(Arrays.asList(authenticationProvider));
    }

    @Bean
    public JwtAuthenticationFilter authenticationTokenFilterBean() throws Exception {
        RequestMatcher postReqMatch = new AntPathRequestMatcher(PATH,
                HttpMethod.PUT.name());
        RequestMatcher putReqMatch = new AntPathRequestMatcher(PATH,
                HttpMethod.POST.name());
        RequestMatcher deleteReqMatch = new AntPathRequestMatcher(PATH,
                HttpMethod.DELETE.name());
        RequestMatcher getReqMatch = new AntPathRequestMatcher(PATH,
                HttpMethod.GET.name());

        RequestMatcher reqMatch = new OrRequestMatcher(postReqMatch, putReqMatch, deleteReqMatch,
                getReqMatch);

        JwtAuthenticationFilter authenticationTokenFilter = new JwtAuthenticationFilter(reqMatch);
        authenticationTokenFilter.setAuthenticationManager(authenticationManager());
        authenticationTokenFilter
                .setAuthenticationSuccessHandler(new JwtAuthenticationSuccessHandler());
        authenticationTokenFilter
                .setAuthenticationFailureHandler(new JwtAuthenticationFailureHandler());
        return authenticationTokenFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.anonymous().and().csrf().disable().authorizeRequests().anyRequest().permitAll().and()
                .exceptionHandling().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().anyRequest().permitAll();
        http.addFilterBefore(authenticationTokenFilterBean(),
                UsernamePasswordAuthenticationFilter.class);
        http.headers().cacheControl();
    }
}
