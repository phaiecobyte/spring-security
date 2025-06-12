package com.phaiecobyte.spring_security.dto;

import lombok.Data;

@Data
public class RegisterReq {
    private String surname;
    private String forename;
    private String email;
    private String password;
}
