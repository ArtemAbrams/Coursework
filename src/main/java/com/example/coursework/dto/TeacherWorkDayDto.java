package com.example.coursework.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherWorkDayDto {
    private String subject;
    private Integer subjectNumber;
    private Long scheduleId;
    private String className;
    private Long taskId;
}
