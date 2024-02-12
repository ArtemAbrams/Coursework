package com.example.coursework.security.services;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.function.Function;

public interface JwtSecurity {
    String extractUsername(String token);
    Claims extractAllClaims(String token);
    <T> T extractClaim(String token, Function<Claims, T> resolveClaims);
    String generateToken(UserDetails userDetails);
    boolean isTokenValid(String token, UserDetails userDetails);
}
