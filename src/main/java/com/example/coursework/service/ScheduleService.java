package com.example.coursework.service;

import com.example.coursework.dto.CurrentDateDto;
import com.example.coursework.dto.response.GetScheduleResponseDto;

import javax.security.sasl.AuthenticationException;

public interface ScheduleService {

    GetScheduleResponseDto getCurrentSchedule(CurrentDateDto currentDateDto) throws AuthenticationException;
}
