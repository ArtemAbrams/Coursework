package com.example.coursework.repositoty;

import com.example.coursework.entity.WorkWeek;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.Optional;

public interface WorkWeekRepository extends JpaRepository<WorkWeek, Long> {

    Optional<WorkWeek> findWorkWeeksByWeekStartAndWeekEnd(Date weekStart, Date weekEnd);
}
