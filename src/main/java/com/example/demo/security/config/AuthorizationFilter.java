package com.example.demo.security.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.demo.security.jwt.JwtUtil;
import com.example.demo.security.jwt.Properties;
import com.example.demo.security.principal.PrincipalDetails;
import com.example.demo.token.model.RefreshToken;
import com.example.demo.token.repository.RefreshTokenRepository;
import com.example.demo.user.model.entity.User;
import com.example.demo.user.model.enumerate.IsActive;
import com.example.demo.user.repository.UserRepository;
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
import java.util.Date;
import java.util.Enumeration;

public class AuthorizationFilter extends BasicAuthenticationFilter {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    public AuthorizationFilter(AuthenticationManager authenticationManager, RefreshTokenRepository refreshTokenRepository, UserRepository userRepository) {
        super(authenticationManager);
        this.refreshTokenRepository = refreshTokenRepository;
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

            User user = userRepository.findByUsernameAndIsActive(username, IsActive.YES).orElseThrow(()-> new IllegalArgumentException("해당 유저가 존재하지 않습니다"));

            if(user == null)
            {
                throw new IllegalArgumentException("유효하지 않은 토큰입니다");
            }

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

            String receivedRefreshToken = request.getHeader(Properties.REFRESH);

            if(receivedRefreshToken == null)
            {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return;
            }

            RefreshToken refreshToken = refreshTokenRepository.findByRefreshToken(receivedRefreshToken).orElse(null);

            if(refreshToken == null)
            {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return;
            }

            if(JWT.decode(refreshToken.getRefreshToken().replace(Properties.PREFIX,"")).getExpiresAt().before(new Date()))
            {
                refreshTokenRepository.deleteByRefreshToken(refreshToken.getRefreshToken());
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return;
            }

            User user = userRepository.findByUsernameAndIsActive(JwtUtil.getClaim(request,Properties.CLAIM_USERNAME), IsActive.YES).orElseThrow(IllegalArgumentException::new);

            //RefreshToken storedToken = refreshTokenRepository.findByUsername(user.getUsername()).orElseThrow(IllegalArgumentException::new);

            //String newRefreshToken = JwtUtil.makeAccessJwt(user.getUsername(), Properties.REFRESH);
            //storedToken.setRefreshToken(newRefreshToken);

            PrincipalDetails principalDetails = new PrincipalDetails(user);
            Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails,
                    null,
                    principalDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            response.setHeader(Properties.HEADER_STRING, JwtUtil.makeAccessJwt(user.getUsername(), Properties.ACCESS));
            //response.setHeader(Properties.REFRESH, newRefreshToken);

            chain.doFilter(request,response);
        }
        catch(IllegalArgumentException e)
        {
            log.warn(e.getMessage());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
    }
}
