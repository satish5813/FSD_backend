package com.klu.ExamPortalAPI.controller;

import com.klu.ExamPortalAPI.model.UserAnswer;
import com.klu.ExamPortalAPI.service.UserAnswerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-answers")
public class UserAnswerController {

    private final UserAnswerService userAnswerService;

    public UserAnswerController(UserAnswerService userAnswerService) {
        this.userAnswerService = userAnswerService;
    }

    // Save user answer
    @PostMapping
    public ResponseEntity<UserAnswer> saveUserAnswer(@RequestBody UserAnswer userAnswer) {
        UserAnswer savedAnswer = userAnswerService.saveUserAnswer(userAnswer);
        return ResponseEntity.ok(savedAnswer);
    }

    // Get answers by student and course
    @GetMapping("/student/{studentId}/course/{courseId}")
    public ResponseEntity<List<UserAnswer>> getAnswersByStudentAndCourse(
            @PathVariable Long studentId, @PathVariable Long courseId) {
        List<UserAnswer> answers = userAnswerService.getAnswersByStudentAndCourse(studentId, courseId);
        return ResponseEntity.ok(answers);
    }
}
