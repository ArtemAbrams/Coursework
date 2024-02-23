package com.example.coursework.repositoty;

import com.example.coursework.entity.StudentClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassRepository extends JpaRepository<StudentClass, Long> {

}
