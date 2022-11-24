package com.example.demo.seed.socket.controller;

import com.example.demo.seed.model.ClientACK;
import com.example.demo.seed.model.Seed;
import com.example.demo.seed.repository.SeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.Header;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@RestController
public class MessageController {
    private final SimpMessagingTemplate template;
    private final SeedRepository seedRepository;

    @MessageMapping("/device") //device -> user
    private void test(@Header String test) //jsonMappingException
    {
        System.out.println(test);
        template.convertAndSend("/topic" + "/user/" + "94:B9:7E:D3:20:64","ACK");
    }

    @MessageMapping("/user") //user -> device
    private void test2(@Payload String test/*ClientACK clientACK*/)
    {
        System.out.println(test);
        template.convertAndSend("/topic", "user -> device" + test);
    }

    @MessageMapping("/test") //for test
    private void test3(@Payload Seed seed)
    {
        template.convertAndSend("/topic" + "/user",seed);
    }
}
