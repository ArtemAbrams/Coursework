package com.example.coursework.dto;

import com.example.coursework.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentTaskAnswerDto {

    private String fileName;
    private Integer grade;
    private TaskStatus taskStatus;
    private byte[] file;
}
