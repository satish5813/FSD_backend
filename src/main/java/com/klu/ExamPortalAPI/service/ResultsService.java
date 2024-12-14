package com.klu.ExamPortalAPI.service;

import com.klu.ExamPortalAPI.model.Results;
import com.klu.ExamPortalAPI.model.ResultDetails;
import com.klu.ExamPortalAPI.repository.ResultsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultsService {

    private final ResultsRepository resultsRepository;

    public ResultsService(ResultsRepository resultsRepository) {
        this.resultsRepository = resultsRepository;
    }

    // Create or update a result
    public Results saveResults(Results results) {
        // Calculate score
        int score = (int) results.getDetails().stream()
                .filter(detail -> detail.getCorrectOption().equals(detail.getStudentAnswer()))
                .count();
        results.setScore(score);
        return resultsRepository.save(results);
    }

    // Get all results
    public List<Results> getAllResults() {
        return resultsRepository.findAll();
    }

    // Get results by student
    public List<Results> getResultsByStudentId(Long studentId) {
        return resultsRepository.findByStudentId(studentId);
    }

    // Get results by course
    public List<Results> getResultsByCourse(String course) {
        return resultsRepository.findByCourse(course);
    }

    // Get result by ID
    public Results getResultsById(Long id) {
        return resultsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Results with ID " + id + " not found."));
    }

    // Delete results by ID
    public void deleteResults(Long id) {
        if (resultsRepository.existsById(id)) {
            resultsRepository.deleteById(id);
        } else {
            throw new RuntimeException("Results with ID " + id + " not found.");
        }
    }
}
