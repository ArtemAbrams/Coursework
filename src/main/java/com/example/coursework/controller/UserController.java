package com.example.coursework.controller;

import com.example.coursework.service.implementation.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;
    @GetMapping("/get-info")
    public ResponseEntity<?> getUser(){
        try {
            return ResponseEntity.ok()
                    .body(userService.getUser());
        }
        catch (Exception exception){
           log.error(exception.getMessage());
           return ResponseEntity.internalServerError()
                   .body(exception.getMessage());
        }
    }
}
