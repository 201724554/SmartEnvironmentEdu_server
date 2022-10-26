package com.example.demo.user.controller;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.example.demo.DTO.ResponseDTO;
import com.example.demo.security.jwt.JwtUtil;
import com.example.demo.security.jwt.Properties;
import com.example.demo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class UserLogoutController {
    private final UserService userService;
    @PostMapping("/logout")
    private ResponseDTO<Object> logoutHandler(HttpServletRequest request)
    {
        if(request.getHeader(Properties.HEADER_STRING) != null)
        {
            try
            {
                String username = JwtUtil.getClaim(request, Properties.CLAIM_USERNAME);
                userService.logout(username);
            }
            catch (JWTDecodeException e)
            {
                return new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), null);
            }
        }
        return new ResponseDTO<>(HttpStatus.OK.value(), null);
    }
}
