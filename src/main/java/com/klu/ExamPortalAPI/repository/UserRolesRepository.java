package com.klu.ExamPortalAPI.repository;

import com.klu.ExamPortalAPI.model.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRolesRepository extends JpaRepository<UserRoles, Long> {
    Optional<UserRoles> findByEmail(String email);
}
