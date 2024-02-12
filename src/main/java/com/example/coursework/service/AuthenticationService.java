package com.example.coursework.service;


import com.example.coursework.dto.request.LoginRequest;
import com.example.coursework.dto.request.RegistrationRequest;
import com.example.coursework.dto.response.TokenResponse;

public interface AuthenticationService {
    String registration(RegistrationRequest registrationData);
    TokenResponse login(LoginRequest loginData);
}
