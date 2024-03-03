package com.example.coursework.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTaskRequestDto {

    private Long taskId;
    private String description;
    private LocalDateTime deadline;
}
