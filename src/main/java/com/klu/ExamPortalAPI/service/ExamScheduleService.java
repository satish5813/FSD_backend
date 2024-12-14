package com.klu.ExamPortalAPI.service;

import com.klu.ExamPortalAPI.model.ExamSchedule;
import com.klu.ExamPortalAPI.repository.ExamScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExamScheduleService {

    private final ExamScheduleRepository examScheduleRepository;

    public ExamScheduleService(ExamScheduleRepository examScheduleRepository) {
        this.examScheduleRepository = examScheduleRepository;
    }

    // Create or update an exam schedule
    public ExamSchedule saveExamSchedule(ExamSchedule examSchedule) {
        return examScheduleRepository.save(examSchedule);
    }

    // Get all exam schedules
    public List<ExamSchedule> getAllExamSchedules() {
        return examScheduleRepository.findAll();
    }

    // Get exam schedules by course
    public List<ExamSchedule> getExamSchedulesByCourseId(Long courseId) {
        return examScheduleRepository.findByCourseId(courseId);
    }

    // Get an exam schedule by ID
    public Optional<ExamSchedule> getExamScheduleById(Long id) {
        return examScheduleRepository.findById(id);
    }

    // Delete an exam schedule by ID
    public void deleteExamSchedule(Long id) {
        if (examScheduleRepository.existsById(id)) {
            examScheduleRepository.deleteById(id);
        } else {
            throw new RuntimeException("Exam Schedule with ID " + id + " not found.");
        }
    }
}
