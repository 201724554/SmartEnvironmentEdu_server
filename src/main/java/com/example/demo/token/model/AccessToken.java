package com.example.demo.token.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccessToken {
    private String username;
    private String accessToken;
}
