package com.example.coursework.dto.response;

import com.example.coursework.dto.WorkDayDto;
import com.example.coursework.enums.WeekDay;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetScheduleResponseDto {

    private Map<WeekDay, List<WorkDayDto>> workDayDtoMap;
}
