package com.example.demo.security.config;

import com.example.demo.redis.entity.AccessToken;
import com.example.demo.redis.repo.AccessTokenRepository;
import com.example.demo.redis.repo.RefreshTokenRepository;
import com.example.demo.security.jwt.JwtUtil;
import com.example.demo.security.jwt.Properties;
import com.example.demo.security.principal.PrincipalDetails;
import com.example.demo.user.model.DTO.LoginDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AccessTokenRepository accessTokenRepository;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        ObjectMapper om = new ObjectMapper();
        LoginDTO loginDTO = null;
        try
        {
            loginDTO = om.readValue(request.getInputStream(), LoginDTO.class);
        }
        catch (Exception e) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }
        if(loginDTO != null)
        {
            try {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        loginDTO.getUsername(), loginDTO.getPassword());

                return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            }
            catch (BadCredentialsException | UsernameNotFoundException e)
            {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
            }
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
        AccessToken accessToken = AccessToken.builder()
                .username(principalDetails.getUsername())
                .accessToken(JwtUtil.makeAccessJwt(principalDetails.getUsername(), Properties.ACCESS_HEADER))
                .build();
        accessTokenRepository.save(accessToken);
        response.setHeader(Properties.ACCESS_HEADER, Properties.PREFIX + accessToken.getAccessToken());
    }
}
