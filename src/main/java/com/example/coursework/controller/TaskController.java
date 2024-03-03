package com.example.coursework.controller;

import com.example.coursework.dto.request.CreateTaskRequestDto;
import com.example.coursework.dto.request.GetSpecifyStudentRequestDto;
import com.example.coursework.dto.request.SpecifyGradeRequestDto;
import com.example.coursework.dto.request.UpdateTaskRequestDto;
import com.example.coursework.service.implementation.TaskManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskManagementService taskManagementService;
    @GetMapping("/get-student-task/{taskId}/answer")
    public ResponseEntity<?> getStudentTask(@PathVariable("taskId") Long taskId){
        try {
            return ResponseEntity.ok(taskManagementService.studentTaskAnswerResponseDto(taskId));
        }
        catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseEntity.internalServerError()
                    .body(exception.getMessage());
        }
    }

    @PostMapping ("/{taskId}/upload-file")
    public ResponseEntity<?> uploadFile(@PathVariable("taskId") Long taskId, MultipartFile file){
        try {
            taskManagementService.uploadAnswerOnTask(taskId, file);

            return ResponseEntity.ok("File successfully upload");
        }
        catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseEntity.internalServerError()
                    .body(exception.getMessage());
        }
    }
    @DeleteMapping("/file/{taskId}/detach")
    public ResponseEntity<?> deleteFile(@PathVariable("taskId") Long taskId){
        try {
             taskManagementService.detachAnswerOnTask(taskId);

            return ResponseEntity.ok("File successfully upload");
        }
        catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseEntity.internalServerError()
                    .body(exception.getMessage());
        }
    }
    @PreAuthorize("hasAuthority('teacher')")
    @PostMapping()
    public ResponseEntity<?> createTask(@RequestBody CreateTaskRequestDto createTaskRequestDto){
        try {
            taskManagementService.createTask(createTaskRequestDto);

           return ResponseEntity
                   .ok()
                   .build();
        }
        catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseEntity.internalServerError()
                    .body(exception.getMessage());
        }
    }
    @PreAuthorize("hasAuthority('teacher')")
    @GetMapping("/{taskId}")
    public ResponseEntity<?> getTask(@PathVariable("taskId") Long taskId){
        try {
            return ResponseEntity
                    .ok(taskManagementService.getTask(taskId));
        }
        catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseEntity.internalServerError()
                    .body(exception.getMessage());
        }
    }
    @PreAuthorize("hasAuthority('teacher')")
    @PutMapping()
    public ResponseEntity<?> updateTask(@RequestBody UpdateTaskRequestDto taskRequestDto){
        try {
            taskManagementService.updateTask(taskRequestDto);

            return ResponseEntity
                    .ok()
                    .build();
        }
        catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseEntity.internalServerError()
                    .body(exception.getMessage());
        }
    }
    @PreAuthorize("hasAuthority('teacher')")
    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable("taskId") Long taskId){
        try {
            taskManagementService.deleteTask(taskId);

            return ResponseEntity
                    .ok()
                    .build();
        }
        catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseEntity.internalServerError()
                    .body(exception.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('teacher')")
    @GetMapping("/solutions/{taskId}")
    public ResponseEntity<?> getSolutions(@PathVariable("taskId") Long taskId){
        try {
            return ResponseEntity
                    .ok(taskManagementService.getSolutions(taskId));
        }
        catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseEntity.internalServerError()
                    .body(exception.getMessage());
        }
    }
    @PreAuthorize("hasAuthority('teacher')")
    @GetMapping("/student/solution/{taskId}/{userEmail}")
    public ResponseEntity<?> getStudentSolution(@PathVariable Long taskId, @PathVariable String userEmail){
        try {
            return ResponseEntity
                    .ok(taskManagementService.getStudentSolution(taskId, userEmail));
        }
        catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseEntity.internalServerError()
                    .body(exception.getMessage());
        }
    }
    @PreAuthorize("hasAuthority('teacher')")
    @PostMapping("/grade")
    public ResponseEntity<?> specifyGrade(@RequestBody SpecifyGradeRequestDto requestDto){
        try {
            taskManagementService.setGradeOnTask(requestDto);

            return ResponseEntity
                    .ok()
                    .build();
        }
        catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseEntity.internalServerError()
                    .body(exception.getMessage());
        }
    }
}
