package com.example.coursework.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "subjects")
@NoArgsConstructor
@AllArgsConstructor
public class Subject extends BasicEntity {
   private String name;
   @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, mappedBy = "subject")
   private List<Schedule> schedules = new ArrayList<>();
}
