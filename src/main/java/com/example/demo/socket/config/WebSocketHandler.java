package com.example.demo.socket.config;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;

@Component
public class WebSocketHandler extends TextWebSocketHandler {
    public static HashMap<String,Object> sessionMap = new HashMap<>();
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        System.out.println("Connection from: "+session.getRemoteAddress()+", session id: "+session.getId());
        sessionMap.put(session.getId(),session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        System.out.println("Disconnection from: "+session.getRemoteAddress()+", session id: "+session.getId());
        sessionMap.remove(session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("text message: "+message.getPayload());

    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        super.handleMessage(session, message);
        System.out.println("message: "+message.getPayload());
        session.sendMessage(new TextMessage("test return"));
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
    }
}
