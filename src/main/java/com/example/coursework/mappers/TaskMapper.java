package com.example.coursework.mappers;

import com.example.coursework.dto.TaskDto;
import com.example.coursework.dto.response.GetTaskResponseDto;
import com.example.coursework.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = ComponentModel.SPRING)
public interface TaskMapper {

    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    @Mapping(source = "description", target = "description")
    @Mapping(source = "deadline", target = "deadline")
    TaskDto taskToTaskDto(Task task);
}
