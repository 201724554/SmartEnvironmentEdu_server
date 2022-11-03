package com.example.demo.security.config;

import com.example.demo.token.repository.RefreshTokenRepository;
import com.example.demo.user.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;


public class AuthorizationFilterApply extends AbstractHttpConfigurer<AuthorizationFilterApply, HttpSecurity> {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public AuthorizationFilterApply(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository)
    {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
    }
    @Override
    public void configure(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        http.addFilterAfter(new AuthorizationFilter(authenticationManager, refreshTokenRepository, userRepository), FilterSecurityInterceptor.class);
    }

    public static AuthorizationFilterApply authorizationFilterApply(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository)
    {
        return new AuthorizationFilterApply(refreshTokenRepository, userRepository);
    }
}
