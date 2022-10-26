package com.example.demo.redis.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RedisHash(value = "educatorSession")
public class EducatorSocketSession {
    @Id
    String educatorId;
    WebSocketSession educatorSession;
    List<WebSocketSession> studentSession;
}
