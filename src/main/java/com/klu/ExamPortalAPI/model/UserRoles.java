package com.klu.ExamPortalAPI.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRoles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String role;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "permissions", joinColumns = @JoinColumn(name = "user_role_id"))
    @Column(name = "permission")
    private List<String> permissions;
}
