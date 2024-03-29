package com.example.coursework.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetSpecifyStudentRequestDto {
    @NotNull
    private String email;
    @NotNull
    private Long taskId;
}
