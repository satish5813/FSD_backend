package com.klu.ExamPortalAPI.repository;

import com.klu.ExamPortalAPI.model.UserCourseMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCourseMappingRepository extends JpaRepository<UserCourseMapping, Long> {
    List<UserCourseMapping> findByUserId(Long userId);

    List<UserCourseMapping> findByCourseId(Long courseId);
}
