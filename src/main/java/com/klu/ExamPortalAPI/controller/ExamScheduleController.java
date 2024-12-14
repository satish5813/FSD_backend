package com.klu.ExamPortalAPI.controller;

import com.klu.ExamPortalAPI.model.ExamSchedule;
import com.klu.ExamPortalAPI.service.ExamScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exam-schedules")
public class ExamScheduleController {

    private final ExamScheduleService examScheduleService;

    public ExamScheduleController(ExamScheduleService examScheduleService) {
        this.examScheduleService = examScheduleService;
    }

    // Create or update an exam schedule
    @PostMapping
    public ResponseEntity<ExamSchedule> createOrUpdateExamSchedule(@RequestBody ExamSchedule examSchedule) {
        ExamSchedule savedExam = examScheduleService.saveExamSchedule(examSchedule);
        return ResponseEntity.ok(savedExam);
    }

    // Get all exam schedules
    @GetMapping
    public List<ExamSchedule> getAllExamSchedules() {
        return examScheduleService.getAllExamSchedules();
    }

    // Get exam schedules by course ID
    @GetMapping("/course/{courseId}")
    public List<ExamSchedule> getExamSchedulesByCourseId(@PathVariable Long courseId) {
        return examScheduleService.getExamSchedulesByCourseId(courseId);
    }

    // Get an exam schedule by ID
    @GetMapping("/{id}")
    public ResponseEntity<ExamSchedule> getExamScheduleById(@PathVariable Long id) {
        return examScheduleService.getExamScheduleById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete an exam schedule by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExamSchedule(@PathVariable Long id) {
        try {
            examScheduleService.deleteExamSchedule(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
