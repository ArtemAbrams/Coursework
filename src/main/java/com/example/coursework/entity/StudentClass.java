package com.example.coursework.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentClass extends BasicEntity {
  @Column(unique = true, updatable = false)
  private String className;
  @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER, mappedBy = "studentClass")
  private List<User> users = new ArrayList<>();
  @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER, mappedBy = "studentClass")
  private List<Schedule> schedules = new ArrayList<>();
}
