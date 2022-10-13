package com.example.demo.security.config;

import com.example.demo.redis.RedisService;
import com.example.demo.redis.repo.AccessTokenRepository;
import com.example.demo.redis.repo.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static com.example.demo.security.config.AuthenticationFilterApply.authenticationFilterApply;
import static com.example.demo.security.config.AuthorizationFilterApply.authorizationFilterApply;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Bean
    public BCryptPasswordEncoder encoder()
    {
        return new BCryptPasswordEncoder();
    }
    private final CorsConfig corsConfig;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AccessTokenRepository accessTokenRepository;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .addFilter(corsConfig.corsFilter())
                .apply(authenticationFilterApply(refreshTokenRepository,accessTokenRepository))
                .and()
                .apply(authorizationFilterApply())
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .logout().disable()
                .authorizeHttpRequests(authorize -> authorize
                        .mvcMatchers("/login","/logout","/register/**").permitAll()
                        .mvcMatchers("/user/**").hasAnyRole("STUDENT","EDUCATOR","MANAGER","ADMIN")
                        .mvcMatchers("/educator/**").hasAnyRole("EDUCATOR","MANAGER","ADMIN")
                        .mvcMatchers("/manager/**").hasAnyRole("MANAGER","ADMIN")
                        .mvcMatchers("/admin/**").hasRole("ADMIN")
                );
        return http.build();
    }
}
