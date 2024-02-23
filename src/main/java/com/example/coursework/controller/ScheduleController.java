package com.example.coursework.controller;

import com.example.coursework.dto.CurrentDateDto;
import com.example.coursework.service.implementation.ScheduleServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;

@Slf4j
@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleServiceImpl scheduleService;

    @GetMapping("/get-schedule")
    public ResponseEntity<?> getSchedule(@RequestParam("week-start") Date weekStart,
            @RequestParam("week-end") Date weekEnd){
        try {
            var currentDate = new CurrentDateDto(weekStart, weekEnd);

            return ResponseEntity.ok(scheduleService.getCurrentSchedule(currentDate));
        }
        catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseEntity.internalServerError()
                    .body(exception.getMessage());
        }
    }
}
