package com.example.demo.security.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.demo.redis.entity.AccessToken;
import com.example.demo.redis.entity.RefreshToken;
import com.example.demo.redis.repo.AccessTokenRepository;
import com.example.demo.redis.repo.RefreshTokenRepository;
import com.example.demo.security.jwt.JwtUtil;
import com.example.demo.security.jwt.Properties;
import com.example.demo.security.principal.PrincipalDetails;
import com.example.demo.user.model.entity.User;
import com.example.demo.user.model.enumerate.IsActive;
import com.example.demo.user.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

public class AuthorizationFilter extends BasicAuthenticationFilter {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
    private final RefreshTokenRepository refreshTokenRepository;
    private final AccessTokenRepository accessTokenRepository;
    private final UserRepository userRepository;
    public AuthorizationFilter(AuthenticationManager authenticationManager, RefreshTokenRepository refreshTokenRepository
            , AccessTokenRepository accessTokenRepository, UserRepository userRepository) {
        super(authenticationManager);
        this.refreshTokenRepository = refreshTokenRepository;
        this.accessTokenRepository = accessTokenRepository;
        this.userRepository = userRepository;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String accessToken = request.getHeader(Properties.HEADER_STRING);
        if(accessToken == null || !accessToken.startsWith(Properties.PREFIX))
        {
            chain.doFilter(request, response);
            return;
        }

        try
        {
            String username = JWT.require(Algorithm.HMAC512(Properties.KEY)).build().verify(accessToken.replace(Properties.PREFIX, ""))
                    .getClaim(Properties.CLAIM_USERNAME).asString();
            AccessToken storedAccessToken = accessTokenRepository.findById(username).orElse(null);

            if(storedAccessToken == null || !accessToken.equals(storedAccessToken.getAccessToken()))
            {
                throw new IllegalArgumentException("저장된 토큰과 일치하지 않습니다");
            }

            User user = userRepository.findByUsernameAndIsActive(username, IsActive.YES).orElseThrow(()-> new IllegalArgumentException("해당 유저가 존재하지 않습니다"));

            RefreshToken storedRefreshToken = refreshTokenRepository.findById(user.getUsername()).orElse(null);

            if(storedRefreshToken == null)
            {
                throw new IllegalArgumentException("refresh 토큰이 만료되었습니다");
            }

            AccessToken newAccessToken = AccessToken.builder()
                    .username(user.getUsername())
                    .accessToken(JwtUtil.makeAccessJwt(user.getUsername(), Properties.ACCESS))
                    .build();
            accessTokenRepository.save(newAccessToken);

            response.setHeader(Properties.HEADER_STRING, newAccessToken.getAccessToken());

            PrincipalDetails principalDetails = new PrincipalDetails(user);
            Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails,
                    null,
                    principalDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            chain.doFilter(request,response);
        }
        catch (TokenExpiredException e)
        {
            log.warn("토큰이 만료되었습니다");
            refreshTokenRepository.deleteById(JwtUtil.getClaim(request,Properties.CLAIM_USERNAME));
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
        catch(IllegalArgumentException e)
        {
            log.warn(e.getMessage());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
    }
}
