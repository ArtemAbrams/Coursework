package com.example.coursework.service.implementation;

import com.example.coursework.dto.CurrentDateDto;
import com.example.coursework.dto.UserDto;
import com.example.coursework.dto.WorkDayDto;
import com.example.coursework.dto.response.GetScheduleResponseDto;
import com.example.coursework.entity.Schedule;
import com.example.coursework.entity.Task;
import com.example.coursework.entity.User;
import com.example.coursework.entity.WorkWeek;
import com.example.coursework.exception.UserNotFoundException;
import com.example.coursework.repositoty.UserRepository;
import com.example.coursework.repositoty.WorkWeekRepository;
import com.example.coursework.service.ScheduleService;
import com.example.coursework.utils.DateUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final UserRepository userRepository;
    private final WorkWeekRepository workWeekRepository;
    @Override
    @Transactional
    public GetScheduleResponseDto getCurrentSchedule(CurrentDateDto currentDateDto) {
        var user = (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        var workWeek = workWeekRepository.findWorkWeeksByWeekStartAndWeekEnd(currentDateDto.getStartDate(),
                currentDateDto.getEndDate())
                .orElseThrow(() -> new IllegalStateException("No work week found for the current date range."));

        var currentUser = userRepository.findUserByEmail(user.getEmail())
                .orElseThrow(() -> new UserNotFoundException("Can not retrieve current user"));

        var scheduleGroupByWeekDay = currentUser.getStudentClass()
                .getSchedules()
                .stream()
                .collect(Collectors.groupingBy(Schedule::getWeekDay));

        var workDayDtoMap = scheduleGroupByWeekDay
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(entry -> entry.getKey().ordinal()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream().map(schedule -> {
                                    var teacherDto = new UserDto(schedule.getTeacher().getFirstName(),
                                            schedule.getTeacher().getLastName());
                                    var subject = schedule.getSubject().getName();
                                    var subjectNumber = schedule.getSubjectNumber();
                                    var taskId = getTask(schedule, workWeek);

                                    return new WorkDayDto(teacherDto, subject, subjectNumber, taskId);
                                })
                                .sorted(Comparator.comparing(WorkDayDto::getSubjectNumber))
                                .collect(Collectors.toList()),
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new
                ));

        return new GetScheduleResponseDto(workDayDtoMap);
    }
    private Long getTask(Schedule schedule, WorkWeek workWeek) {
        var task = getTaskCurrentWeek(schedule, workWeek);

        if(!task.isPresent()){
            return null;
        }

       return task.get()
                .getId();
    }
    private Optional<Task> getTaskCurrentWeek(Schedule schedule, WorkWeek workWeek){

        return schedule.getTasks()
                .stream()
                .filter(e -> e.getWorkWeek().equals(workWeek))
                .findFirst();
    }
}
