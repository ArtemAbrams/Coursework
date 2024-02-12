package com.example.coursework.service.implementation;


import com.example.coursework.dto.request.LoginRequest;
import com.example.coursework.dto.request.RegistrationRequest;
import com.example.coursework.dto.response.TokenResponse;
import com.example.coursework.entity.Role;
import com.example.coursework.entity.User;
import com.example.coursework.repositoty.RoleRepository;
import com.example.coursework.repositoty.UserRepository;
import com.example.coursework.security.services.implementation.JwtServiceIml;
import com.example.coursework.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtServiceIml jwtServiceIml;

    @Override
    public String registration(RegistrationRequest registrationData) {
        var role = roleRepository.findRoleByRole("User")
                .orElseThrow(() -> new RuntimeException("Role was not found"));

        var user = User
                .builder()
                .enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .email(registrationData.getEmail())
                .password(passwordEncoder.encode(registrationData.getPassword()))
                .roles(List.of(role))
                .build();

        userRepository.saveAndFlush(user);

        return "You have registered";
    }

    @Override
    @Transactional
    public TokenResponse login(LoginRequest loginData) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
          loginData.getEmail(),
          loginData.getPassword()
         )
        );

        var user = userRepository.findUserByEmail(loginData.getEmail())
                .orElseThrow(() -> new RuntimeException("User was not found"));

        var listRole = user.getRoles()
                .stream()
                .map(Role::getRole)
                .toList();

        return TokenResponse
                .builder()
                .token(jwtServiceIml.generateToken(user))
                .roleDTOList(listRole)
                .build();
    }
}
