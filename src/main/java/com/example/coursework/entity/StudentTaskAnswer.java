package com.example.coursework.entity;

import com.example.coursework.enums.TaskStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentTaskAnswer extends BasicEntity {
    @Column(name = "file_name")
    private String fileName;
    @Min(1)
    @Max(12)
    private Integer grade;
    @Enumerated(value = EnumType.STRING)
    private TaskStatus taskStatus;
    @Column(name = "pdf_data")
    private String keyName;
    @ManyToOne
    private Task task;
    @ManyToOne
    private User user;
}
