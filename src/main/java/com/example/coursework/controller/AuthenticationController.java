package com.example.coursework.controller;

import com.example.coursework.dto.request.LoginRequest;
import com.example.coursework.dto.request.RegistrationRequest;
import com.example.coursework.dto.response.TokenResponse;
import com.example.coursework.service.implementation.AuthenticationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationServiceImpl authenticationServiceImpl;
    @PostMapping("/registration")
    public ResponseEntity<String> registration(@RequestBody RegistrationRequest registrationData){
        return ResponseEntity.ok(authenticationServiceImpl.registration(registrationData));
    }
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginData){
        return ResponseEntity.ok(authenticationServiceImpl.login(loginData));
    }
}
