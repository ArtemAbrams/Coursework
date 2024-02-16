package com.example.coursework.entity;

import com.example.coursework.enums.WeekDay;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Schedule extends BasicEntity {
   @Enumerated(value = EnumType.STRING)
   private WeekDay weekDay;
   @ManyToOne
   private Class aClass;
   @ManyToOne
   private User teacher;
   @ManyToOne
   private Subject subject;
   @Min(1)
   @Max(7)
   private Integer subjectNumber;
   @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER, mappedBy = "schedule")
   private List<Task> tasks;
}
