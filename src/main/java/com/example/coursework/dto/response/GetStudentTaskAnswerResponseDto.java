package com.example.coursework.dto.response;

import com.example.coursework.dto.StudentTaskAnswerDto;
import com.example.coursework.dto.TaskDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetStudentTaskAnswerResponseDto {

    private TaskDto taskDto;
    private StudentTaskAnswerDto studentTaskAnswerDto;
}
