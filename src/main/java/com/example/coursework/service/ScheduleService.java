package com.example.coursework.service;

import com.example.coursework.dto.CurrentDateDto;
import com.example.coursework.dto.response.GetStudentScheduleResponseDto;
import com.example.coursework.dto.response.GetTeacherScheduleResponseDto;

import javax.security.sasl.AuthenticationException;

public interface ScheduleService {

    GetStudentScheduleResponseDto getCurrentStudentSchedule(CurrentDateDto currentDateDto) throws AuthenticationException;
    GetTeacherScheduleResponseDto getTeacherScheduleResponseDto(CurrentDateDto currentDateDto);
}
