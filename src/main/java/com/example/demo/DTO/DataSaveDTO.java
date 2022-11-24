package com.example.demo.DTO;

import com.example.demo.seed.model.Seed;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataSaveDTO {
    List<String> data;
}
