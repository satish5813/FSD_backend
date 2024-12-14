package com.klu.ExamPortalAPI.repository;

import com.klu.ExamPortalAPI.model.QuestionBank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionBankRepository extends JpaRepository<QuestionBank, Long> {
    List<QuestionBank> findByCourseId(Long courseId);
    
}
