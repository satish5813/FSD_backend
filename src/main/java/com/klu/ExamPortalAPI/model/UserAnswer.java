package com.klu.ExamPortalAPI.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long studentId; // Reference to the student

    @Column(nullable = false)
    private Long courseId; // Reference to the course

    @Column(nullable = false)
    private LocalDate examDate; // Date of the exam

    @Column(nullable = false)
    private String question; // Question text

    @Column(nullable = false)
    private String correctOption; // Correct answer

    @Column(nullable = false)
    private String studentAnswer; // Student's answer
}
