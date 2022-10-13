package com.example.demo.redis.entity;

import com.example.demo.security.jwt.Properties;
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
@RedisHash(value = "accessToken")
public class AccessToken {
    @Id
    private String username;
    private String accessToken;
    @TimeToLive
    @Builder.Default
    private long expireTime = Properties.ACCESS_EXPIRE_TIME;

    void setAccessToken(String accessToken)
    {
        this.accessToken = accessToken;
    }
}
