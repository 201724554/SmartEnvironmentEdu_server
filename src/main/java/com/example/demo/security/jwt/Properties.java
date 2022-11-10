package com.example.demo.security.jwt;

public interface Properties {
    String KEY = "spzkfkznqo";
    String PREFIX = "Bearer ";
    String HEADER_STRING = "authorization";
    String ACCESS = "access";
    String REFRESH = "refresh";
    String CLAIM_USERNAME = "username";
    long ACCESS_EXPIRE_TIME = 1800;
    long REFRESH_EXPIRE_TIME = 86400;
}
