package com.example.coursework.configuration;


import com.example.coursework.entity.WorkWeek;
import com.example.coursework.repositoty.WorkWeekRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
@RequiredArgsConstructor
public class SchoolWeekInitializer implements CommandLineRunner {

    private final WorkWeekRepository workWeekRepository;
    @Override
    public void run(String... args) throws Exception {
        long count = workWeekRepository.count();
        if (count == 0) {
            generateWeeks();
        }
    }
    private void generateWeeks() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.SEPTEMBER, 1);

        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DATE, 1);
        }

        while (calendar.get(Calendar.YEAR) < 2024 || (calendar.get(Calendar.YEAR) == 2024 && calendar.get(Calendar.MONTH) < Calendar.JUNE)) {
            var weekStart = new java.sql.Date(calendar.getTimeInMillis());

            calendar.add(Calendar.DATE, 4);
            var weekEnd = new java.sql.Date(calendar.getTimeInMillis());

            calendar.add(Calendar.DATE, 3);

            var schoolWeek = WorkWeek.builder()
                    .weekStart(weekStart)
                    .weekEnd(weekEnd)
                    .build();

            workWeekRepository.save(schoolWeek);
        }
    }
}
