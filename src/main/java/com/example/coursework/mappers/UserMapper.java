package com.example.coursework.mappers;

import com.example.coursework.dto.UserDto;
import com.example.coursework.dto.UserPassTaskDto;
import com.example.coursework.dto.response.GetUserResponseDto;
import com.example.coursework.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = ComponentModel.SPRING)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    @Mapping(source = "email", target = "email")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    GetUserResponseDto userToUserResponseDto(User user);
}
