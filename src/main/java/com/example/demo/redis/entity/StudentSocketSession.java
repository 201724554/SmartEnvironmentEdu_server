package com.example.demo.redis.entity;

import com.example.demo.security.jwt.Properties;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.web.socket.WebSocketSession;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RedisHash(value = "studentSession")
public class StudentSocketSession {
    @Id
    String studentId;
    WebSocketSession studentSession;
}
