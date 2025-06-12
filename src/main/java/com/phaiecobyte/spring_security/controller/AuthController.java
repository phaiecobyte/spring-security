package com.phaiecobyte.spring_security.controller;

import com.phaiecobyte.spring_security.dto.JwtAuthenticationRes;
import com.phaiecobyte.spring_security.dto.LoginReq;
import com.phaiecobyte.spring_security.dto.RefreshTokenReq;
import com.phaiecobyte.spring_security.dto.RegisterReq;
import com.phaiecobyte.spring_security.entity.User;
import com.phaiecobyte.spring_security.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<User> Register(@RequestBody RegisterReq req){
        return ResponseEntity.ok(authenticationService.register(req));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationRes> login(@RequestBody LoginReq req){
        return ResponseEntity.ok(authenticationService.login(req));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<JwtAuthenticationRes> refreshToken(@RequestBody RefreshTokenReq req){
        return ResponseEntity.ok(authenticationService.refreshToken(req));
    }

}
