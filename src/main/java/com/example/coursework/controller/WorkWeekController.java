package com.example.coursework.controller;

import com.example.coursework.service.implementation.WorkWeekServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/work-week")
public class WorkWeekController {

    private final WorkWeekServiceImpl workWeekService;

    @GetMapping("/current-week")
    public ResponseEntity<?> getCurrentWeek(){
        try {
            return ResponseEntity.ok(workWeekService.getCurrentWorkWeek());
        }
        catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseEntity.internalServerError()
                    .body(exception.getMessage());
        }
    }
}
