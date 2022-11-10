package com.example.demo.seed.socket.config;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.LinkedHashMap;

@Component
@RequiredArgsConstructor
public class ClientSocketHandler extends TextWebSocketHandler {
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println(session.getAttributes());
        super.afterConnectionEstablished(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        int jsonStart = payload.lastIndexOf('{');
        int jsonEnd = payload.lastIndexOf('}');

        if(jsonStart != -1 && jsonEnd != -1)
        {
            if(jsonStart > jsonEnd)
            {
                //exception
            }
            String jsonString = payload.substring(jsonStart, jsonEnd+1);

            JSONParser jsonParser = new JSONParser(jsonString);
            LinkedHashMap<String, Object> json = jsonParser.object();
            json.forEach((idx, elem) -> System.out.println(elem));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
    }
}
