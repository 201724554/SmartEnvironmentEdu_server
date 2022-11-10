package com.example.demo.seed.socket.controller;

import com.example.demo.seed.model.ClientACK;
import com.example.demo.seed.model.Seed;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MessageController {
    private final SimpMessagingTemplate template;

    @MessageMapping("/device") //device -> user
    private void test(@Payload String test) //jsonMappingException
    {
        System.out.println(test);
        template.convertAndSend("/topic","ACK");
    }

    @MessageMapping("/user") //user -> device
    private void test2(@Payload ClientACK clientACK)
    {
        System.out.println(clientACK);
        //template.convertAndSend("/topic/"+clientACK.getMAC(),"user -> device");
    }
}
