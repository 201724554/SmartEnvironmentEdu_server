package com.example.demo.security.config;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

public class AuthorizationFilterApply extends AbstractHttpConfigurer<AuthorizationFilterApply, HttpSecurity> {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        http.addFilterAfter(new AuthorizationFilter(authenticationManager), FilterSecurityInterceptor.class);
    }

    public static AuthorizationFilterApply authorizationFilterApply()
    {
        return new AuthorizationFilterApply();
    }
}
