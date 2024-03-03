package com.example.coursework.service;

import com.example.coursework.dto.request.CreateTaskRequestDto;
import com.example.coursework.dto.request.GetSpecifyStudentRequestDto;
import com.example.coursework.dto.request.SpecifyGradeRequestDto;
import com.example.coursework.dto.request.UpdateTaskRequestDto;
import com.example.coursework.dto.response.GetStudentTaskAnswerResponseDto;
import com.example.coursework.dto.response.GetTaskResponseDto;
import com.example.coursework.dto.response.GetUserSolutionsResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface TaskService {

    GetStudentTaskAnswerResponseDto studentTaskAnswerResponseDto(Long taskId);
    void uploadAnswerOnTask(Long taskId, MultipartFile file) throws IOException;
    void deleteTask(Long taskId);
    void createTask(CreateTaskRequestDto createTaskRequestDto);
    GetTaskResponseDto getTask(Long taskId);
    void updateTask(UpdateTaskRequestDto taskRequestDto);
    void detachAnswerOnTask(Long taskId);

    GetUserSolutionsResponseDto getSolutions(Long taskId);

    GetStudentTaskAnswerResponseDto getStudentSolution(Long taskId, String userEmail);

    void setGradeOnTask(SpecifyGradeRequestDto requestDto);
}
