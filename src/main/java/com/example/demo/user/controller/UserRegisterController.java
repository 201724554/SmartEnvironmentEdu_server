package com.example.demo.user.controller;

import com.example.demo.DTO.ResponseDTO;
import com.example.demo.redis.entity.RegisterAuthNum;
import com.example.demo.user.model.DTO.RegisterDTO;
import com.example.demo.user.model.entity.Educator;
import com.example.demo.user.model.entity.Student;
import com.example.demo.user.model.entity.User;
import com.example.demo.user.model.enumerate.IsActive;
import com.example.demo.user.model.enumerate.IsAuthorized;
import com.example.demo.user.model.enumerate.Role;
import com.example.demo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;
import java.util.Random;


@RequiredArgsConstructor
@RestController
public class UserRegisterController {
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 일반 user, student 관련 api
    */
    @PostMapping("/register")
    private ResponseDTO<Object> register(@Valid @RequestBody RegisterDTO registerDTO)
    {
        if(userService.checkDuplicateUsernameAndEmail(registerDTO.getUsername(), registerDTO.getEmail()))
        {
            throw new IllegalArgumentException();
        }
        Random random = new Random();
        String randomAuthNum = String.format("%04d", random.nextInt(10000));

        Student student = Student.studentBuilder()
                .username(registerDTO.getUsername())
                .password(bCryptPasswordEncoder.encode(registerDTO.getPassword()))
                .email(registerDTO.getEmail())
                .role(Role.STUDENT)
                .isActive(IsActive.NO)
                .build();

        RegisterAuthNum registerAuthNum = RegisterAuthNum.builder()
                .email(student.getEmail())
                .registerAuthNum(randomAuthNum)
                .build();
        userService.addUser(student,registerAuthNum);

        return new ResponseDTO<>(HttpStatus.OK.value(),null);
    }

    /**
     * educator 관련 api
    */
    @PostMapping("/register/educator")
    private ResponseDTO<Object> registerEducator(@Valid @RequestBody RegisterDTO registerDTO)
    {
        if(userService.checkDuplicateUsernameAndEmail(registerDTO.getUsername(), registerDTO.getEmail()))
        {
            throw new IllegalArgumentException();
        }
        Random random = new Random();
        String randomAuthNum = String.format("%04d", random.nextInt(10000));

        Educator educator = Educator.educatorBuilder()
                .username(registerDTO.getUsername())
                .password(bCryptPasswordEncoder.encode(registerDTO.getPassword()))
                .email(registerDTO.getEmail())
                .role(Role.EDUCATOR)
                .isActive(IsActive.NO)
                .isAuthorized(IsAuthorized.NO)
                .build();
        RegisterAuthNum registerAuthNum = RegisterAuthNum.builder()
                .email(educator.getEmail())
                .registerAuthNum(randomAuthNum)
                .build();
        userService.addUser(educator,registerAuthNum);

        return new ResponseDTO<>(HttpStatus.OK.value(),null);
    }

    /**
     * 공용 api
    */
    @PatchMapping("/register/auth")
    private ResponseDTO<Object> confirmAuthentication(@RequestBody Map<String,String> map)
    {
        userService.confirmAuthentication(map.get("username"),map.get("email"),map.get("authNum"));
        return new ResponseDTO<>(HttpStatus.OK.value(), null);
    }
    @PostMapping("/register/resend")
    private ResponseDTO<Object> resendAuthNum(@RequestBody Map<String,String> map)
    {
        Random random = new Random();
        String randomAuthNum = String.format("%04d", random.nextInt(10000));

        RegisterAuthNum registerAuthNum = RegisterAuthNum.builder()
                .email(map.get("email"))
                .registerAuthNum(randomAuthNum)
                .build();
        userService.resendAuthNum(registerAuthNum);

        return new ResponseDTO<>(HttpStatus.OK.value(),null);
    }

    /**
     * 예외처리
     */

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private void methodArgumentNotValidExceptionHandler(HttpServletResponse response)
    {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
    }

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

    /**
     * test
     */
    @GetMapping("/test")
    private String test()
    {
        return "Test";
    }
}
