package com.example.demo.redis.entity.session;

import com.example.demo.security.jwt.Properties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.web.socket.WebSocketSession;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RedisHash(value = "deviceSession")
public class DeviceSocketSession {
    @Id
    private String deviceMAC;
    private String username;
    private WebSocketSession deviceSession;
    @TimeToLive
    @Builder.Default
    private long expireTime = Properties.ACCESS_EXPIRE_TIME;
}
