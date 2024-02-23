package com.example.coursework.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetCurrentWorkWeekDto {

    private Long id;
    private Date weekStart;
    private Date weekEnd;
}
