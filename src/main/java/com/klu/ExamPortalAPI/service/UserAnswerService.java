package com.klu.ExamPortalAPI.service;

import com.klu.ExamPortalAPI.model.UserAnswer;
import com.klu.ExamPortalAPI.repository.UserAnswerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAnswerService {

    private final UserAnswerRepository userAnswerRepository;

    public UserAnswerService(UserAnswerRepository userAnswerRepository) {
        this.userAnswerRepository = userAnswerRepository;
    }

    // Save user answer
    public UserAnswer saveUserAnswer(UserAnswer userAnswer) {
        return userAnswerRepository.save(userAnswer);
    }

    // Get answers by student and course
    public List<UserAnswer> getAnswersByStudentAndCourse(Long studentId, Long courseId) {
        return userAnswerRepository.findByStudentIdAndCourseId(studentId, courseId);
    }
}
