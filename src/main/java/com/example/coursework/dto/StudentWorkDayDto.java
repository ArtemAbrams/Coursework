package com.example.coursework.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentWorkDayDto {

    private UserDto teacher;
    private String subject;
    private Integer subjectNumber;
    private Long taskId;
}
