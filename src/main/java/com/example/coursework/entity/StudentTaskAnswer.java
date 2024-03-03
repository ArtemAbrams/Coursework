package com.example.coursework.entity;

import com.example.coursework.enums.TaskStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentTaskAnswer extends BasicEntity {
    @Column(name = "file_name")
    private String fileName;
    private LocalDateTime passedDate;
    @Min(1)
    @Max(12)
    private Integer grade;
    @Enumerated(value = EnumType.STRING)
    private TaskStatus taskStatus;
    private String keyName;
    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;
    @ManyToOne
    private User user;
}
