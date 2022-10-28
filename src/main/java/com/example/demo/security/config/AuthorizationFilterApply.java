package com.example.demo.security.config;

import com.example.demo.redis.repo.token.AccessTokenRepository;
import com.example.demo.redis.repo.token.RefreshTokenRepository;
import com.example.demo.user.repo.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;


public class AuthorizationFilterApply extends AbstractHttpConfigurer<AuthorizationFilterApply, HttpSecurity> {
    private final RefreshTokenRepository refreshTokenRepository;
    private final AccessTokenRepository accessTokenRepository;
    private final UserRepository userRepository;

    public AuthorizationFilterApply(RefreshTokenRepository refreshTokenRepository, AccessTokenRepository accessTokenRepository, UserRepository userRepository)
    {
        this.refreshTokenRepository = refreshTokenRepository;
        this.accessTokenRepository = accessTokenRepository;
        this.userRepository = userRepository;
    }
    @Override
    public void configure(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        http.addFilterAfter(new AuthorizationFilter(authenticationManager, refreshTokenRepository, accessTokenRepository, userRepository), FilterSecurityInterceptor.class);
    }

    public static AuthorizationFilterApply authorizationFilterApply(RefreshTokenRepository refreshTokenRepository
            ,AccessTokenRepository accessTokenRepository
            ,UserRepository userRepository)
    {
        return new AuthorizationFilterApply(refreshTokenRepository,accessTokenRepository,userRepository);
    }
}
