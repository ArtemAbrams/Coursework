package com.example.coursework.service.implementation;

import com.example.coursework.dto.response.GetCurrentWorkWeekDto;
import com.example.coursework.mappers.WorkWeekMapper;
import com.example.coursework.repositoty.WorkWeekRepository;
import com.example.coursework.service.WorkWeekService;
import com.example.coursework.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkWeekServiceImpl implements WorkWeekService {

    private final WorkWeekRepository workWeekRepository;
    @Override
    public GetCurrentWorkWeekDto getCurrentWorkWeek() {
        var currentDate = DateUtils.getCurrentDate();

        var currentWorkWeek = workWeekRepository.findWorkWeeksByWeekStartAndWeekEnd(currentDate.getStartDate(),
                        currentDate.getEndDate())
               .orElseThrow(()-> new IllegalStateException("No work week found for the current date range."));

        return  WorkWeekMapper.INSTANCE.workWeektoGetCurrentWorkWeekDto(currentWorkWeek);
    }
}
