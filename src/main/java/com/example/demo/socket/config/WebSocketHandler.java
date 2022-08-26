package com.example.demo.socket.config;

import com.example.demo.model.ACK;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

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
        JSONParser jsonParser = new JSONParser(message.getPayload());
        try
        {
            LinkedHashMap<String, Object> json = jsonParser.object();
            json.forEach((idx,elem)->{
                System.out.println(idx + " " + elem);
            });

            ACK ack = ACK.builder()
                    .status(200)
                    .measure(true)
                    .censor("")
                    .mean(-1)
                    .refresh(false)
                    .build();

            Map<String, Object> data = new HashMap<>();
            data.put("ACK", ack);

            try
            {
                String jsonACK = new ObjectMapper().writeValueAsString(data);
                System.out.println("ACK "+jsonACK);
                session.sendMessage(new TextMessage(jsonACK));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        super.handleMessage(session, message);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
        System.out.println(exception.getMessage());
    }

    @Override
    protected void handlePongMessage(WebSocketSession session, PongMessage message) throws Exception {
        super.handlePongMessage(session, message);
        System.out.println("pong");
    }


}
