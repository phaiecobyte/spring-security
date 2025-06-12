package com.phaiecobyte.spring_security.dto;

import lombok.Data;

@Data
public class JwtAuthenticationRes {
    private String token;
    private String refreshToken;
}
