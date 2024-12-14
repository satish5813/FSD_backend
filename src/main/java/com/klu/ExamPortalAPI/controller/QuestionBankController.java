package com.klu.ExamPortalAPI.controller;

import com.klu.ExamPortalAPI.model.QuestionBank;
import com.klu.ExamPortalAPI.service.QuestionBankService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionBankController {

    private final QuestionBankService questionBankService;

    public QuestionBankController(QuestionBankService questionBankService) {
        this.questionBankService = questionBankService;
    }

    // Create or update a question
    @PostMapping
    public ResponseEntity<QuestionBank> createOrUpdateQuestion(@RequestBody QuestionBank questionBank) {
        QuestionBank savedQuestion = questionBankService.saveQuestion(questionBank);
        return ResponseEntity.ok(savedQuestion);
    }

    // Get all questions
    @GetMapping
    public List<QuestionBank> getAllQuestions() {
        return questionBankService.getAllQuestions();
    }

    // Get questions by course ID
    @GetMapping("/course/{courseId}")
    public List<QuestionBank> getQuestionsByCourseId(@PathVariable Long courseId) {
        return questionBankService.getQuestionsByCourseId(courseId);
    }

    // Get question by ID
    @GetMapping("/{id}")
    public ResponseEntity<QuestionBank> getQuestionById(@PathVariable Long id) {
        try {
            QuestionBank question = questionBankService.getQuestionById(id);
            return ResponseEntity.ok(question);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Update a question
    @PutMapping("/{id}")
    public ResponseEntity<QuestionBank> updateQuestion(@PathVariable Long id, @RequestBody QuestionBank updatedQuestion) {
        try {
            QuestionBank question = questionBankService.updateQuestion(id, updatedQuestion);
            return ResponseEntity.ok(question);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a question
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        try {
            questionBankService.deleteQuestion(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("/upload")
    public ResponseEntity<List<QuestionBank>> uploadQuestions(@RequestParam("file") MultipartFile file) {
        try {
            List<QuestionBank> savedQuestions = questionBankService.uploadQuestionsFromCSV(file.getInputStream());
            return ResponseEntity.ok(savedQuestions);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
