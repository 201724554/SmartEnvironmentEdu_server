package com.example.demo.device.controller;

import com.example.demo.DTO.AddMACDTO;
import com.example.demo.DTO.MacListDTO;
import com.example.demo.DTO.ResponseDTO;
import com.example.demo.device.model.UserDevice;
import com.example.demo.device.service.UserDeviceService;
import com.example.demo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserDeviceController {
    private final UserDeviceService userDeviceService;
    private final UserService userService;

    @PostMapping("/manager/device")
    private ResponseDTO<Object> addDevice(@Valid @RequestBody AddMACDTO addMACDTO)
    {
        UserDevice userDevice = UserDevice.builder()
                .userDeviceMAC(addMACDTO.getMAC())
                .user(userService.getUser(addMACDTO.getUsername()))
                .build();
        userDeviceService.addDevice(userDevice);
        return new ResponseDTO<>(HttpStatus.OK.value(), null);
    }

    @PostMapping("/user/device")
    private ResponseDTO<Object> registerDevice(@Valid @RequestBody AddMACDTO addMACDTO)
    {
        userDeviceService.registerDevice(addMACDTO.getUsername(), addMACDTO.getMAC());
        return new ResponseDTO<>(HttpStatus.OK.value(), null);
    }

    @GetMapping("/user/device/{username}")
    private ResponseDTO<List<MacListDTO>> getDeviceList(@PathVariable String username)
    {
        return new ResponseDTO<>(HttpStatus.OK.value(), userDeviceService.getDeviceList(username));
    }

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
}
