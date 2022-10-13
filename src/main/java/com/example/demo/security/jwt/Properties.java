package com.example.demo.security.jwt;

public interface Properties {
    String KEY = "spzkfkznqo";
    String PREFIX = "Bearer ";
    String ACCESS_HEADER = "access";
    String REFRESH_HEADER = "refresh";
    String CLAIM_USERNAME = "username";
    long ACCESS_EXPIRE_TIME = 1800;
    long REFRESH_EXPIRE_TIME = 86400;
}
