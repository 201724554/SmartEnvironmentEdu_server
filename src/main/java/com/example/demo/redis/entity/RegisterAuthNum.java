package com.example.demo.redis.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RedisHash(value = "registerAuthNum")
public class RegisterAuthNum {
    @Id
    private String email;
    private String registerAuthNum;
    @TimeToLive
    @Builder.Default
    private long expireTime = 300;
}
