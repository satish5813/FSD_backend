package com.klu.ExamPortalAPI.repository;

import com.klu.ExamPortalAPI.model.ExamSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamScheduleRepository extends JpaRepository<ExamSchedule, Long> {
    List<ExamSchedule> findByCourseId(Long courseId);
}
