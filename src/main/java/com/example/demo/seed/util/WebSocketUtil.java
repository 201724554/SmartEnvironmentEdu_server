package com.example.demo.seed.util;

import com.example.demo.seed.socket.config.WebSocketHandler;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public class WebSocketUtil {
    public static void send(String sessionId, String message) throws IOException
    {
        System.out.println("send");
        WebSocketSession webSocketSession = (WebSocketSession) WebSocketHandler.sessionMap.get(sessionId);
        webSocketSession.sendMessage(new TextMessage(message));
    }
}