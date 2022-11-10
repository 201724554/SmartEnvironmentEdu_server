package com.example.demo.security.config;

import com.example.demo.token.repository.RefreshTokenRepository;
import com.example.demo.user.repository.UserRepository;
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
    private final UserRepository userRepository;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .addFilter(corsConfig.corsFilter())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .apply(authenticationFilterApply(refreshTokenRepository))
                .and()
                .apply(authorizationFilterApply(refreshTokenRepository, userRepository))
                .and()
                .logout().disable()
                .authorizeHttpRequests(authorize -> authorize
                        .mvcMatchers("/login","/register/**","/logout","/device/**","/client/socket/**","/test/**").permitAll()
                        .mvcMatchers("/user/**").hasAnyRole("STUDENT","EDUCATOR","MANAGER","ADMIN")
                        .mvcMatchers("/educator/**").hasAnyRole("EDUCATOR","MANAGER","ADMIN")
                        .mvcMatchers("/manager/**").hasAnyRole("MANAGER","ADMIN")
                        .mvcMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().denyAll()
                );
        return http.build();
    }
}
