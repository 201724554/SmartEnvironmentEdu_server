package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
public class RegisterDTO {
    @Pattern(regexp = "^[\\w_]{5,20}$")
    String username;
    @Pattern(regexp = "^.*(?=^.{8,20}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$")
    String password;
    @Pattern(regexp = "^[\\da-zA-Z]([-_.]?[\\da-zA-Z])*@[\\da-zA-Z]([-_.]?[\\da-zA-Z])*.[a-zA-Z]{2,3}$")
    String email;
}
