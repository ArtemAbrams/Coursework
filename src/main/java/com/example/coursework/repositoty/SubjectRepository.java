package com.example.coursework.repositoty;

import com.example.coursework.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    Optional<Subject> findSubjectByName(String name);
}
