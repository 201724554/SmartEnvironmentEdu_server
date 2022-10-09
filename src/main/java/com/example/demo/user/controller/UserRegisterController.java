package com.example.demo.user.controller;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.mail.service.MailService;
import com.example.demo.user.model.entity.Educator;
import com.example.demo.user.model.entity.Student;
import com.example.demo.user.model.enumerate.Role;
import com.example.demo.user.service.UserRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;


@RequiredArgsConstructor
@RestController
public class UserRegisterController {
    private final UserRegisterService userRegisterService;

    /**
     * 일반 user, student 관련 api
    */
    @PostMapping("/register")
    private ResponseDTO<Object> register(@RequestBody Student student)
    {
        if(userRegisterService.checkDuplicateUsernameAndEmail(student.getUsername(), student.getEmail()))
        {
            throw new IllegalArgumentException();
        }
        userRegisterService.addUser(student);

        return new ResponseDTO<>(HttpStatus.OK.value(),null);
    }

    /**
     * educator 관련 api
    */
    @PostMapping("/register/educator")
    private ResponseDTO<Object> registerEducator(@RequestBody Educator educator)
    {
        if(userRegisterService.checkDuplicateUsernameAndEmail(educator.getUsername(), educator.getEmail()))
        {
            throw new IllegalArgumentException();
        }
        educator.setRole(Role.EDUCATOR.toString());
        userRegisterService.addUser(educator);

        return new ResponseDTO<>(HttpStatus.OK.value(),null);
    }

    /**
     * 공용 api
    */
    @PatchMapping("/register/auth")
    private ResponseDTO<Object> confirmAuthentication(@RequestBody Map<String,String> map)
    {
        userRegisterService.confirmAuthentication(map.get("username"),map.get("email"),map.get("authNum"));
        return new ResponseDTO<>(HttpStatus.OK.value(), null);
    }
    @PostMapping("/register/resend")
    private ResponseDTO<Object> resendAuthNum(@RequestBody Map<String,String> map)
    {
        userRegisterService.resendAuthNum(map.get("email"));
        return new ResponseDTO<>(HttpStatus.OK.value(),null);
    }

    /**
     * 예외처리
     */

    @ExceptionHandler(IllegalArgumentException.class)
    private void illegalArgumentExceptionHandler(HttpServletResponse response)
    {
        response.setStatus(HttpStatus.CONFLICT.value());
    }
    @ExceptionHandler(MailException.class)
    private void mailSendExceptionHandler(HttpServletResponse response)
    {
       response.setStatus(HttpStatus.SERVICE_UNAVAILABLE.value());
    }
}
