package com.example.coursework.dto.response;

import com.example.coursework.dto.TaskDto;
import com.example.coursework.dto.UserDto;
import com.example.coursework.dto.UserPassTaskDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetUserSolutionsResponseDto {

    private TaskDto taskDto;
    private List<UserPassTaskDto> userPassTaskDtos;
}
