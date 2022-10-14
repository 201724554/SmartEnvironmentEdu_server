package com.example.demo.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class JwtUtil {
    public static String makeAccessJwt(String username, String type)
    {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() +
                        (type.equals(Properties.REFRESH_HEADER)
                                ? Properties.REFRESH_EXPIRE_TIME * 1000
                                : Properties.ACCESS_EXPIRE_TIME * 1000)))
                .withClaim(Properties.CLAIM_USERNAME,username)
                .sign(Algorithm.HMAC512(Properties.KEY));
    }

    public static String getClaim(HttpServletRequest request, String claim)
    {
        String token = request.getHeader(Properties.ACCESS_HEADER);
        return JWT.decode(token.replace(Properties.PREFIX,"")).getClaim(claim).asString();
    }
}
