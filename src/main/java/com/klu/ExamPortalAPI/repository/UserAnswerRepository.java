package com.klu.ExamPortalAPI.repository;

import com.klu.ExamPortalAPI.model.UserAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAnswerRepository extends JpaRepository<UserAnswer, Long> {
    List<UserAnswer> findByStudentIdAndCourseId(Long studentId, Long courseId);
}
