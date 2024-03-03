package com.example.coursework.service.implementation;

import com.example.coursework.dto.CurrentDateDto;
import com.example.coursework.dto.TeacherWorkDayDto;
import com.example.coursework.dto.UserDto;
import com.example.coursework.dto.StudentWorkDayDto;
import com.example.coursework.dto.response.GetStudentScheduleResponseDto;
import com.example.coursework.dto.response.GetTeacherScheduleResponseDto;
import com.example.coursework.entity.BasicEntity;
import com.example.coursework.entity.Schedule;
import com.example.coursework.entity.Task;
import com.example.coursework.entity.User;
import com.example.coursework.entity.WorkWeek;
import com.example.coursework.enums.WeekDay;
import com.example.coursework.exception.UserNotFoundException;
import com.example.coursework.repositoty.UserRepository;
import com.example.coursework.repositoty.WorkWeekRepository;
import com.example.coursework.service.ScheduleService;
import com.example.coursework.utils.UserUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final UserRepository userRepository;
    private final WorkWeekRepository workWeekRepository;
    private static final String NO_WORK_WEEK_FOUND_ERROR = "No work week found for the current date range.";
    private static final String CANNOT_RETRIEVE_CURRENT_USER_ERROR = "Cannot retrieve current user";

    @Override
    @Transactional
    public GetStudentScheduleResponseDto getCurrentStudentSchedule(CurrentDateDto currentDateDto) {
        var user = UserUtils.getCurrentUser();
        var workWeek = getWorkWeek(currentDateDto);
        var currentUser = getCurrentUser(user.getEmail());

        var scheduleGroupByWeekDay = currentUser.getStudentClass()
                .getSchedules()
                .stream()
                .collect(Collectors.groupingBy(Schedule::getWeekDay));

        var workDayDtoMap = buildWorkDayDtoMap(scheduleGroupByWeekDay, workWeek, this::mapToStudentWorkDayDto);

        return new GetStudentScheduleResponseDto(workDayDtoMap);
    }

    @Override
    public GetTeacherScheduleResponseDto getTeacherScheduleResponseDto(CurrentDateDto currentDateDto) {
        var user = UserUtils.getCurrentUser();
        var workWeek = getWorkWeek(currentDateDto);
        var currentUser = getCurrentUser(user.getEmail());

        var scheduleGroupByWeekDay = currentUser.getSchedules()
                .stream()
                .collect(Collectors.groupingBy(Schedule::getWeekDay));

        var workDayDtoMap = buildWorkDayDtoMap(scheduleGroupByWeekDay, workWeek, this::mapToTeacherWorkDayDto);

        return new GetTeacherScheduleResponseDto(workDayDtoMap);
    }

    private WorkWeek getWorkWeek(CurrentDateDto currentDateDto) {
        return workWeekRepository.findWorkWeeksByWeekStartAndWeekEnd(currentDateDto.getStartDate(), currentDateDto.getEndDate())
                .orElseThrow(() -> new IllegalStateException(NO_WORK_WEEK_FOUND_ERROR));
    }

    private User getCurrentUser(String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(CANNOT_RETRIEVE_CURRENT_USER_ERROR));
    }

    private <T> LinkedHashMap<WeekDay, List<T>> buildWorkDayDtoMap(Map<WeekDay, List<Schedule>> scheduleGroupByWeekDay, WorkWeek workWeek, BiFunction<List<Schedule>, WorkWeek, List<T>> mapper) {
        return scheduleGroupByWeekDay.entrySet().stream()
                .sorted(Comparator.comparing(entry -> entry.getKey().ordinal()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> mapper.apply(entry.getValue(), workWeek),
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new
                ));
    }

    private List<StudentWorkDayDto> mapToStudentWorkDayDto(List<Schedule> schedules, WorkWeek workWeek) {
        return schedules.stream()
                .map(schedule -> new StudentWorkDayDto(
                        new UserDto(schedule.getTeacher().getEmail(), schedule.getTeacher().getFirstName(), schedule.getTeacher().getLastName()),
                        schedule.getSubject().getName(),
                        schedule.getSubjectNumber(),
                        getTaskId(schedule, workWeek)))
                .sorted(Comparator.comparing(StudentWorkDayDto::getSubjectNumber))
                .collect(Collectors.toList());
    }

    private List<TeacherWorkDayDto> mapToTeacherWorkDayDto(List<Schedule> schedules, WorkWeek workWeek) {
        return schedules.stream()
                .map(schedule -> new TeacherWorkDayDto(
                        schedule.getSubject().getName(),
                        schedule.getSubjectNumber(),
                        schedule.getId(),
                        schedule.getStudentClass().getClassName(),
                        getTaskId(schedule, workWeek)))
                .sorted(Comparator.comparing(TeacherWorkDayDto::getSubjectNumber))
                .collect(Collectors.toList());
    }

    private Long getTaskId(Schedule schedule, WorkWeek workWeek) {
        return schedule.getTasks().stream()
                .filter(task -> task.getWorkWeek().equals(workWeek))
                .findFirst()
                .map(BasicEntity::getId)
                .orElse(null);
    }
}
