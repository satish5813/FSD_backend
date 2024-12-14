package com.klu.ExamPortalAPI.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Column(nullable = false, unique = true)
    private String username;

    @Email(message = "Email should be valid")
    @Column(nullable = false, unique = true)
    private String email;

    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    private String mobile;

    @Column(nullable = true) // This can be null during POST
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private String roles; // e.g., "USER", "ADMIN"
}
