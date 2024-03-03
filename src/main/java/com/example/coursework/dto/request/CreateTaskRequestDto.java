package com.example.coursework.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTaskRequestDto {
    private String description;
    private LocalDateTime deadline;
    private String className;
    private Long scheduleId;
    private Date weekStart;
    private Date weekEnd;
}
