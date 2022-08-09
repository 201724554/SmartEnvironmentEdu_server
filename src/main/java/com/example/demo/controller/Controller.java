package com.example.demo.controller;

import com.example.demo.socket.util.WebSocketUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
public class Controller {
    @GetMapping("/test/{sessionId}")
    public void test(@PathVariable String sessionId)
    {
        try{
            WebSocketUtil.send(sessionId, "send test");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
