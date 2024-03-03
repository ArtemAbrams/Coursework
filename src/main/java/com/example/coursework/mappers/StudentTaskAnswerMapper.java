package com.example.coursework.mappers;

import com.example.coursework.dto.StudentTaskAnswerDto;
import com.example.coursework.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.factory.Mappers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Mapper(componentModel = ComponentModel.SPRING)
public interface StudentTaskAnswerMapper {

    StudentTaskAnswerMapper INSTANCE = Mappers.getMapper(StudentTaskAnswerMapper.class);

    @Mapping(target = "file", ignore = true)
    StudentTaskAnswerDto studentAnswerToStudentAnswerDto(StudentTaskAnswer taskAnswer);

    default StudentTaskAnswerDto studentAnswerToStudentAnswerDtoWithFile(StudentTaskAnswer taskAnswer, InputStream file) {
        StudentTaskAnswerDto dto = studentAnswerToStudentAnswerDto(taskAnswer);
        dto.setFile(convertInputStreamToByteArray(file));

        return dto;
    }
    default byte[] convertInputStreamToByteArray(InputStream inputStream) {
        try (ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
            int nRead;
            byte[] data = new byte[1024];
            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
            return buffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
