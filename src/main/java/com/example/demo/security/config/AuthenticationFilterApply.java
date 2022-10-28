package com.example.demo.security.config;

import com.example.demo.redis.repo.token.AccessTokenRepository;
import com.example.demo.redis.repo.token.RefreshTokenRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

public class AuthenticationFilterApply extends AbstractHttpConfigurer<AuthenticationFilterApply, HttpSecurity> {
    private final RefreshTokenRepository refreshTokenRepository;
    private final AccessTokenRepository accessTokenRepository;

    public AuthenticationFilterApply(RefreshTokenRepository refreshTokenRepository, AccessTokenRepository accessTokenRepository)
    {
        this.refreshTokenRepository = refreshTokenRepository;
        this.accessTokenRepository = accessTokenRepository;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        http.addFilter(new AuthenticationFilter(authenticationManager,refreshTokenRepository,accessTokenRepository));
    }

    public static AuthenticationFilterApply authenticationFilterApply(RefreshTokenRepository refreshTokenRepository,AccessTokenRepository accessTokenRepository)
    {
        return new AuthenticationFilterApply(refreshTokenRepository,accessTokenRepository);
    }
}
