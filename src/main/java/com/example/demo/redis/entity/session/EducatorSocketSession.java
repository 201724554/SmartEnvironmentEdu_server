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

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RedisHash(value = "educatorSession")
public class EducatorSocketSession {
    @Id
    String educatorId;
    WebSocketSession educatorSession;
    Map<String, WebSocketSession> studentSession;
    @TimeToLive
    @Builder.Default
    private long expireTime = Properties.ACCESS_EXPIRE_TIME;
}
