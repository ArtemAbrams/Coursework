package com.example.coursework.repositoty;

import com.example.coursework.entity.StudentClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClassRepository extends JpaRepository<StudentClass, Long> {

    Optional<StudentClass> findStudentClassByClassName(String name);
}
