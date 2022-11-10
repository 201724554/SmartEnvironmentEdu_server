package com.example.demo.seed.socket.config;

import com.example.demo.seed.model.ACK;
import com.example.demo.seed.model.Seed;
import com.example.demo.seed.service.SeedService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TestSocketHandler extends TextWebSocketHandler {
    private final SeedService seedService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> toRemove = new ArrayList<>();
        try{
            JSONParser jsonParser = new JSONParser(message.getPayload());
            LinkedHashMap<String, Object> json = jsonParser.object();
            json.forEach((idx, elem) -> {
                if (elem.equals("NaN")) {
                    toRemove.add(idx);
                }
            });
            toRemove.forEach(json::remove);
            Seed seed = objectMapper.convertValue(json, Seed.class);
            ACK ack = seedService.handleReceivedData(seed);
            String jsonACK = new ObjectMapper().writeValueAsString(ack);
            session.sendMessage(new TextMessage(jsonACK));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
