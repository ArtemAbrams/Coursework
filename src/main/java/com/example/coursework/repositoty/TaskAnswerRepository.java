package com.example.coursework.repositoty;

import com.example.coursework.entity.StudentTaskAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskAnswerRepository extends JpaRepository<StudentTaskAnswer, Long> {

}
