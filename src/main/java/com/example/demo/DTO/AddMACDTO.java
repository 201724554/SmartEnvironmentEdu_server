package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
public class AddMACDTO {
    @Pattern(regexp = "^[\\da-zA-Z][\\da-zA-Z]:[\\da-zA-Z][\\da-zA-Z]:[\\da-zA-Z][\\da-zA-Z]:[\\da-zA-Z][\\da-zA-Z]:[\\da-zA-Z][\\da-zA-Z]:[\\da-zA-Z][\\da-zA-Z]$")
    String MAC;

    String username;
}
