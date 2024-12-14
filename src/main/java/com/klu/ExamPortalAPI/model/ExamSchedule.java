package com.klu.ExamPortalAPI.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long courseId; // Reference to the course ID

    @Column(nullable = false)
    private LocalDate examDate; // Date of the exam

    @Column(nullable = false)
    private LocalTime startTime; // Start time of the exam

    @Column(nullable = false)
    private Integer durationMinutes; // Duration in minutes

    @Column(nullable = false)
    private String dayOfWeek; // Day of the week

    @Column(nullable = false)
    private Integer studentCount; // Total number of students
}
