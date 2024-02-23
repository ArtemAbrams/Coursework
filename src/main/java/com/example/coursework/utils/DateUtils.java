package com.example.coursework.utils;

import com.example.coursework.dto.CurrentDateDto;
import lombok.experimental.UtilityClass;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;

@UtilityClass
public class DateUtils {

    public CurrentDateDto getCurrentDate(){
        var currentDate = LocalDate.now();
        var startOfWeek = currentDate.with(DayOfWeek.MONDAY);
        var endOfWeek = currentDate.with(DayOfWeek.FRIDAY);

        var startDate = Date.valueOf(startOfWeek);
        var endDate = Date.valueOf(endOfWeek);

        return new CurrentDateDto(startDate, endDate);
    }
}
