package com.phaiecobyte.spring_security.dto;

import lombok.Data;

@Data
public class LoginReq {
    private String email;
    private String password;
}
