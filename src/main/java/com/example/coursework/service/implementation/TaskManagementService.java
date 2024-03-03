package com.example.coursework.service.implementation;

import com.example.coursework.dto.StudentTaskAnswerDto;
import com.example.coursework.dto.UserPassTaskDto;
import com.example.coursework.dto.request.CreateTaskRequestDto;
import com.example.coursework.dto.request.SpecifyGradeRequestDto;
import com.example.coursework.dto.request.UpdateTaskRequestDto;
import com.example.coursework.dto.response.GetStudentTaskAnswerResponseDto;
import com.example.coursework.dto.response.GetTaskResponseDto;
import com.example.coursework.dto.response.GetUserSolutionsResponseDto;
import com.example.coursework.entity.Schedule;
import com.example.coursework.entity.StudentTaskAnswer;
import com.example.coursework.entity.Task;
import com.example.coursework.entity.User;
import com.example.coursework.entity.WorkWeek;
import com.example.coursework.enums.TaskStatus;
import com.example.coursework.exception.TaskAlreadyExistException;
import com.example.coursework.exception.UserNotFoundException;
import com.example.coursework.mappers.StudentTaskAnswerMapper;
import com.example.coursework.mappers.TaskMapper;
import com.example.coursework.repositoty.ScheduleRepository;
import com.example.coursework.repositoty.TaskAnswerRepository;
import com.example.coursework.repositoty.TaskRepository;
import com.example.coursework.repositoty.UserRepository;
import com.example.coursework.repositoty.WorkWeekRepository;
import com.example.coursework.service.TaskService;
import com.example.coursework.utils.UserUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Table;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskManagementService implements TaskService {

    private final TaskRepository taskRepository;
    private final S3ServiceImpl s3Service;
    private final TaskAnswerRepository answerRepository;
    private final WorkWeekRepository weekRepository;
    private final ScheduleRepository scheduleRepository;
    private final EntityManager entityManager;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public GetStudentTaskAnswerResponseDto studentTaskAnswerResponseDto(Long taskId) {
        var task = findTaskById(taskId);
        var taskDto = TaskMapper.INSTANCE.taskToTaskDto(task);
        var userTaskAnswer = getStudentTask(task);

        var studentAnswerDto = userTaskAnswer
                .map(this::getStudentTaskAnswer)
                .orElse(new StudentTaskAnswerDto());

        return GetStudentTaskAnswerResponseDto.builder()
                .taskDto(taskDto)
                .studentTaskAnswerDto(studentAnswerDto)
                .build();
    }

    @Override
    @Transactional
    public void uploadAnswerOnTask(Long taskId, MultipartFile file) throws IOException {
        var task = findTaskById(taskId);
        var userTaskAnswer = getStudentTask(task);

        if (userTaskAnswer.isPresent()) {
            StudentTaskAnswer answer = userTaskAnswer.get();
            updateExistingAnswer(file, answer);
        } else {
            createNewAnswerForTask(taskId, file);
        }
    }

    @Override
    @Transactional
    public void deleteTask(Long taskId) {
        var task = findTaskById(taskId);
        checkTeacherHasTask(task);
        task.getStudentTaskAnswers()
                .forEach(this::deleteAnswerFile);

        var deleteTask = entityManager.createQuery("DELETE FROM Task t WHERE t.id = :taskId");
        deleteTask.setParameter("taskId", taskId);
        deleteTask.executeUpdate();

        entityManager.flush();
    }
    private void checkTeacherHasTask(Task task){
        var taskTeacher = task.getSchedule()
                .getTeacher();
        if(!taskTeacher.equals(UserUtils.getCurrentUser()))
            throw new RuntimeException("You did not create this task");
    }
    @Override
    @Transactional
    public void detachAnswerOnTask(Long taskId){
        var task = findTaskById(taskId);
        getStudentTask(task).ifPresent(this::detachStudentTaskAnswer);
    }

    @Override
    public GetUserSolutionsResponseDto getSolutions(Long taskId) {
        var task = findTaskById(taskId);
        checkTeacherHasTask(task);

        var taskDto = TaskMapper.INSTANCE.taskToTaskDto(task);

        var usersSolution = task.getStudentTaskAnswers()
                .stream()
                .map(e -> {
                    var email = e.getUser()
                            .getEmail();
                    var firstName = e.getUser()
                            .getFirstName();
                    var lastName = e.getUser()
                            .getLastName();
                    var isUserPassed = Objects.nonNull(e.getPassedDate());
                    var grade = e.getGrade();

                    return UserPassTaskDto.builder()
                            .userEmail(email)
                            .firstName(firstName)
                            .lastName(lastName)
                            .isPassed(isUserPassed)
                            .grade(grade)
                            .build();
                })
                .toList();

        return new GetUserSolutionsResponseDto(taskDto, usersSolution);
    }

    @Override
    public GetStudentTaskAnswerResponseDto getStudentSolution(Long taskId, String userEmail) {
        var user = findUserByEmail(userEmail);
        var task = findTaskById(taskId);
        var taskDto = TaskMapper.INSTANCE.taskToTaskDto(task);
        var userAnswer = getTaskAnswerByUser(task, user);

        var studentAnswerDto = userAnswer
                .map(this::getStudentTaskAnswer)
                .orElse(new StudentTaskAnswerDto());

        return GetStudentTaskAnswerResponseDto.builder()
                .taskDto(taskDto)
                .studentTaskAnswerDto(studentAnswerDto)
                .build();

    }

    @Override
    @Transactional
    public void setGradeOnTask(SpecifyGradeRequestDto requestDto) {
        var user = findUserByEmail(requestDto.getUserEmail());
        var task = findTaskById(requestDto.getTaskId());
        var taskAnswer = getTaskAnswerByUser(task, user)
                .orElseThrow(() -> new RuntimeException("task answer not found"));

        taskAnswer.setGrade(requestDto.getGrade());
        taskAnswer.setTaskStatus(TaskStatus.Checked);

        answerRepository.saveAndFlush(taskAnswer);
    }

    private Optional<StudentTaskAnswer> getTaskAnswerByUser(Task task, User user) {
        return task.getStudentTaskAnswers().stream()
                .filter(answer -> answer.getUser().equals(user))
                .findFirst();
    }
    private User findUserByEmail(String email){
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with email " + email + " was not found"));
    }
    private void detachStudentTaskAnswer(StudentTaskAnswer answer){
        s3Service.deleteFile(answer.getKeyName());
        answer.setPassedDate(null);
        answer.setFileName(null);
    }
    @Override
    @Transactional
    public void createTask(CreateTaskRequestDto createTaskRequestDto) {
        var workWeek = findWorkWeekByDateRange(createTaskRequestDto.getWeekStart(), createTaskRequestDto.getWeekEnd());
        var schedule = findScheduleById(createTaskRequestDto.getScheduleId());
        checkIfTaskExists(schedule, workWeek);

        var studentAnswers = prepareStudentAnswers(schedule);
        var task = prepareNewTask(createTaskRequestDto, schedule, workWeek, studentAnswers);

        taskRepository.saveAndFlush(task);
    }

    @Override
    public GetTaskResponseDto getTask(Long taskId) {
       var task = findTaskById(taskId);
       var taskDto = TaskMapper.INSTANCE.taskToTaskDto(task);

       return GetTaskResponseDto.builder()
               .taskDto(taskDto)
               .build();
    }

    @Override
    @Transactional
    public void updateTask(UpdateTaskRequestDto taskRequestDto) {
        var task = findTaskById(taskRequestDto.getTaskId());

        task.setDescription(taskRequestDto.getDescription());
        task.setDeadline(taskRequestDto.getDeadline());
    }

    private Task findTaskById(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new UsernameNotFoundException("Task with ID " + taskId + " was not found"));
    }

    private WorkWeek findWorkWeekByDateRange(Date start, Date end) {
        return weekRepository.findWorkWeeksByWeekStartAndWeekEnd(start, end)
                .orElseThrow(() -> new IllegalStateException("No work week found for the current date range."));
    }

    private Schedule findScheduleById(Long scheduleId) {
        return scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalStateException("Schedule with ID " + scheduleId + " not found"));
    }

    private void checkIfTaskExists(Schedule schedule, WorkWeek workWeek) {
        boolean taskExists = schedule.getTasks().stream()
                .anyMatch(task -> task.getWorkWeek().equals(workWeek));

        if (taskExists) {
            throw new TaskAlreadyExistException("Task for the given week and schedule already exists.");
        }
    }

    private List<StudentTaskAnswer> prepareStudentAnswers(Schedule schedule) {
        return schedule.getStudentClass().getUsers().stream()
                .filter(user -> user.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .noneMatch("teacher"::equals))
                .map(user -> StudentTaskAnswer.builder()
                        .user(user)
                        .taskStatus(TaskStatus.Unchecked)
                        .keyName(UUID.randomUUID().toString())
                        .build())
                .toList();
    }

    private Task prepareNewTask(CreateTaskRequestDto dto, Schedule schedule, WorkWeek week, List<StudentTaskAnswer> answers) {
        var newTask = Task.builder()
                .deadline(dto.getDeadline())
                .description(dto.getDescription())
                .schedule(schedule)
                .workWeek(week)
                .build();

        answers.forEach(newTask::addStudentTaskAnswer);
        return newTask;
    }

    private void updateExistingAnswer(MultipartFile file, StudentTaskAnswer answer) throws IOException {
        s3Service.uploadFile(answer.getKeyName(), file);
        answer.setFileName(file.getOriginalFilename());
        answer.setPassedDate(LocalDateTime.now());
        answerRepository.saveAndFlush(answer);
    }

    private void createNewAnswerForTask(Long taskId, MultipartFile file) throws IOException {
        var currentUser = UserUtils.getCurrentUser();
        var task = findTaskById(taskId);
        var keyName = UUID.randomUUID().toString();

        var newAnswer = StudentTaskAnswer.builder()
                .user(currentUser)
                .task(task)
                .taskStatus(TaskStatus.Unchecked)
                .fileName(file.getOriginalFilename())
                .keyName(keyName)
                .passedDate(LocalDateTime.now())
                .build();

        task.addStudentTaskAnswer(newAnswer);
        answerRepository.saveAndFlush(newAnswer);
    }

    private void deleteAnswerFile(StudentTaskAnswer answer) {
        s3Service.deleteFile(answer.getKeyName());

        var query = entityManager.createQuery("DELETE FROM StudentTaskAnswer sta WHERE sta.task.id = :taskId");
        query.setParameter("taskId", answer.getId());
        query.executeUpdate();
    }

    private StudentTaskAnswerDto getStudentTaskAnswer(StudentTaskAnswer taskAnswer) {
        if (s3Service.isFileExist(taskAnswer.getKeyName())) {
            var fileStream = s3Service.getFileAsStream(taskAnswer.getKeyName());
            return StudentTaskAnswerMapper.INSTANCE.studentAnswerToStudentAnswerDtoWithFile(taskAnswer, fileStream);
        }
        return StudentTaskAnswerMapper.INSTANCE.studentAnswerToStudentAnswerDto(taskAnswer);
    }

    private Optional<StudentTaskAnswer> getStudentTask(Task task) {
        var currentUser = UserUtils.getCurrentUser();
        return task.getStudentTaskAnswers().stream()
                .filter(answer -> answer.getUser().equals(currentUser))
                .findFirst();
    }
}

