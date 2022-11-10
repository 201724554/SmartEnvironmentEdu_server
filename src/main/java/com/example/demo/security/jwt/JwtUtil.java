package com.example.demo.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class JwtUtil {
    public static String makeAccessJwt(String username, String type)
    {
        return Properties.PREFIX +
                JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() +
                        (type.equals(Properties.REFRESH)
                                ? Properties.REFRESH_EXPIRE_TIME * 1000
                                : Properties.ACCESS_EXPIRE_TIME * 1000)))
                .withClaim(Properties.CLAIM_USERNAME,username)
                .sign(Algorithm.HMAC512(Properties.KEY));
    }

    public static String getClaim(HttpServletRequest request, String claim)
    {
        String token = request.getHeader(Properties.HEADER_STRING);
        return JWT.decode(token.replace(Properties.PREFIX,"")).getClaim(claim).asString();
    }
    public static boolean isTokenExpired(String token) throws JWTDecodeException
    {
        return JWT.decode(token.replace(Properties.PREFIX, "")).getExpiresAt().before(new Date());
    }
}
