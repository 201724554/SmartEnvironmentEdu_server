package com.example.demo.controller;

//import WebSocketUtil;
import com.example.demo.seed.util.WebSocketUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class Controller {

    @GetMapping("/test/{sessionId}")
    public void test(@PathVariable String sessionId)
    {
        try{
            System.out.println("controller");
            WebSocketUtil.send(sessionId, "send CustomHandshakeHandler");
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
