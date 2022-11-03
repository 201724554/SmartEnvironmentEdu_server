package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
public class AddMACDTO {
    String username;
    @Pattern(regexp = "^[\\da-zA-Z][\\da-zA-Z]-[\\da-zA-Z][\\da-zA-Z]-[\\da-zA-Z][\\da-zA-Z]-[\\da-zA-Z][\\da-zA-Z]-[\\da-zA-Z][\\da-zA-Z]-[\\da-zA-Z][\\da-zA-Z]$")
    String MAC;
}
