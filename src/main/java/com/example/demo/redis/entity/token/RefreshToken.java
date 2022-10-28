package com.example.demo.redis.entity.token;

import com.example.demo.security.jwt.Properties;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RedisHash(value = "refreshToken")
public class RefreshToken {
    @Id
    private String username;
    private String refreshToken;
    @TimeToLive
    @Builder.Default
    private long expireTime = Properties.REFRESH_EXPIRE_TIME;
}
