package com.klu.ExamPortalAPI.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Course name cannot be null")
    @Column(nullable = false, unique = true)
    private String name;

    @NotNull(message = "Course code cannot be null")
    @Column(nullable = false, unique = true)
    private String code;

    private String description;

    @NotNull(message = "Schedule cannot be null")
    private LocalDate schedule;
}
