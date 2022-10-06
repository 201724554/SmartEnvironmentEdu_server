package com.example.demo.socket.config;

import com.example.demo.model.ACK;
import com.example.demo.model.Seed;
import com.example.demo.repository.SeedRepository;
import com.example.demo.service.SeedService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TestSocketConfig extends TextWebSocketHandler {
    private final SeedService seedService;
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
