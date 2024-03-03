package com.example.coursework.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecifyGradeRequestDto {

    private Long taskId;
    private String userEmail;
    private Integer grade;
}
