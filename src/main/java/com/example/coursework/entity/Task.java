package com.example.coursework.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PreRemove;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tasks")
public class Task extends BasicEntity{

    private String description;
    private LocalDateTime deadline;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "task")
    private List<StudentTaskAnswer> studentTaskAnswers;
    @ManyToOne
    private Schedule schedule;
    @ManyToOne
    private WorkWeek workWeek;

    public void addStudentTaskAnswer(StudentTaskAnswer answer) {
        if (studentTaskAnswers == null) {
            studentTaskAnswers = new ArrayList<>();
        }
        studentTaskAnswers.add(answer);
        answer.setTask(this);
    }
}
