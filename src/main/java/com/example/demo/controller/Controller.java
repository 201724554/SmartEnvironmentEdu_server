package com.example.demo.controller;

//import com.example.demo.socket.util.WebSocketUtil;
import com.example.demo.socket.util.WebSocketUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class Controller {

    @GetMapping("/test/{sessionId}")
    public void test(@PathVariable String sessionId)
    {
        try{
            System.out.println("controller");
            WebSocketUtil.send(sessionId, "send test");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    @GetMapping("/home")
    public String home()
    {
        return "home";
    }
}
