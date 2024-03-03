package com.example.coursework.dto.response;

import com.example.coursework.dto.TeacherWorkDayDto;
import com.example.coursework.enums.WeekDay;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTeacherScheduleResponseDto {

    private Map<WeekDay, List<TeacherWorkDayDto>> teacherWorkDayDtoMap;
}
