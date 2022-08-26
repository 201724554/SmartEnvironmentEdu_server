package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ACK {
    private int status;
    private boolean measure;
    private String censor;
    private float mean;
    private boolean refresh;
}
