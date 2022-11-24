package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MacListDTO {
    private String username;
    private List<String> macList;
}
