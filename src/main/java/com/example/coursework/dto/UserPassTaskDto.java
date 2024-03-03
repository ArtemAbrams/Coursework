package com.example.coursework.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPassTaskDto {

    private String userEmail;
    private String firstName;
    private String lastName;
    private Integer grade;
    private boolean isPassed;
}
