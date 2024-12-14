package com.klu.ExamPortalAPI.repository;

import com.klu.ExamPortalAPI.model.Results;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultsRepository extends JpaRepository<Results, Long> {
    List<Results> findByStudentId(Long studentId);

    List<Results> findByCourse(String course);
}
