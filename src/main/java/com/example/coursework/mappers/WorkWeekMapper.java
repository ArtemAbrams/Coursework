package com.example.coursework.mappers;

import com.example.coursework.dto.response.GetCurrentWorkWeekDto;
import com.example.coursework.entity.WorkWeek;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = ComponentModel.SPRING)
public interface WorkWeekMapper {
    WorkWeekMapper INSTANCE = Mappers.getMapper(WorkWeekMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "weekStart", target = "weekStart")
    @Mapping(source = "weekEnd", target = "weekEnd")
    GetCurrentWorkWeekDto toGetCurrentWorkWeekDto(WorkWeek week);
}
