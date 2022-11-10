package com.example.demo.security.config;

import com.example.demo.token.repository.RefreshTokenRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

public class AuthenticationFilterApply extends AbstractHttpConfigurer<AuthenticationFilterApply, HttpSecurity> {
    private final RefreshTokenRepository refreshTokenRepository;

    public AuthenticationFilterApply(RefreshTokenRepository refreshTokenRepository)
    {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        http.addFilter(new AuthenticationFilter(authenticationManager, refreshTokenRepository));
    }

    public static AuthenticationFilterApply authenticationFilterApply(RefreshTokenRepository refreshTokenRepository)
    {
        return new AuthenticationFilterApply(refreshTokenRepository);
    }
}
