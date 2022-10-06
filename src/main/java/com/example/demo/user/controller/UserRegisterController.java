package com.example.demo.user.controller;

import com.example.demo.user.exception.UserRegisterException;
import com.example.demo.user.model.User;
import com.example.demo.user.service.UserRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserRegisterController {
    private final UserRegisterService userRegisterService;

    @PostMapping("/register")
    private void register(@RequestBody User user)
    {
        System.out.println("start");
        System.out.println("done");
    }

    @ExceptionHandler(UserRegisterException.class)
    private String userRegisterExceptionHandler()
    {
        System.out.println("user register exception");
        return "no";
    }
}
