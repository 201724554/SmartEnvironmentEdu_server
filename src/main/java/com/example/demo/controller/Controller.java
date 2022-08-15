package com.example.demo.controller;

import com.example.demo.socket.util.WebSocketUtil;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/testt")
    public void test2(@RequestBody Map<String,Object> mp)
    {
        System.out.println(mp);
    }
}
