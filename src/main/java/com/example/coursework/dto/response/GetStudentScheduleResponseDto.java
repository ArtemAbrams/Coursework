package com.example.coursework.dto.response;

import com.example.coursework.dto.StudentWorkDayDto;
import com.example.coursework.enums.WeekDay;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetStudentScheduleResponseDto {

    private Map<WeekDay, List<StudentWorkDayDto>> workDayDtoMap;
}
