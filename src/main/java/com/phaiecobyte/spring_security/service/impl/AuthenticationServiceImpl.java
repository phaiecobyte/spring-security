package com.phaiecobyte.spring_security.service.impl;

import com.phaiecobyte.spring_security.dto.JwtAuthenticationRes;
import com.phaiecobyte.spring_security.dto.LoginReq;
import com.phaiecobyte.spring_security.dto.RefreshTokenReq;
import com.phaiecobyte.spring_security.dto.RegisterReq;
import com.phaiecobyte.spring_security.entity.Role;
import com.phaiecobyte.spring_security.entity.User;
import com.phaiecobyte.spring_security.repository.UserRepository;
import com.phaiecobyte.spring_security.service.AuthenticationService;
import com.phaiecobyte.spring_security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public JwtAuthenticationRes login(LoginReq req) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(),req.getPassword())
        );

        var user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(()-> new IllegalArgumentException("Invalid email or password"));
        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        JwtAuthenticationRes jwtAuthenticationRes = new JwtAuthenticationRes();

        jwtAuthenticationRes.setToken(jwt);
        jwtAuthenticationRes.setRefreshToken(refreshToken);

        return jwtAuthenticationRes;
    }

    @Override
    public User register(RegisterReq req) {
        User user = new User();

        user.setSurname(req.getSurname());
        user.setForename(req.getForename());
        user.setEmail(req.getEmail());
        user.setRole(Role.ROLE_USER);
        user.setPassword(passwordEncoder.encode(req.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public JwtAuthenticationRes refreshToken(RefreshTokenReq req) {
        String email = jwtService.extractUsername(req.getToken());
        User user = userRepository.findByEmail(email).orElseThrow();
        if(jwtService.isTokenValid(req.getToken(),user)){
            var jwt = jwtService.generateToken(user);

            JwtAuthenticationRes jwtAuthenticationRes = new JwtAuthenticationRes();

            jwtAuthenticationRes.setToken(jwt);
            jwtAuthenticationRes.setRefreshToken(req.getToken());
            return jwtAuthenticationRes;
        }
        return null;
    }
}
