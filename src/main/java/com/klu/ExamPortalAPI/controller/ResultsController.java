package com.klu.ExamPortalAPI.controller;

import com.klu.ExamPortalAPI.model.Results;
import com.klu.ExamPortalAPI.service.ResultsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/results")
public class ResultsController {

    private final ResultsService resultsService;

    public ResultsController(ResultsService resultsService) {
        this.resultsService = resultsService;
    }

    // Create or update a result
    @PostMapping
    public ResponseEntity<Results> createOrUpdateResults(@RequestBody Results results) {
        Results savedResults = resultsService.saveResults(results);
        return ResponseEntity.ok(savedResults);
    }

    // Get all results
    @GetMapping
    public List<Results> getAllResults() {
        return resultsService.getAllResults();
    }

    // Get results by student ID
    @GetMapping("/student/{studentId}")
    public List<Results> getResultsByStudentId(@PathVariable Long studentId) {
        return resultsService.getResultsByStudentId(studentId);
    }

    // Get results by course
    @GetMapping("/course/{course}")
    public List<Results> getResultsByCourse(@PathVariable String course) {
        return resultsService.getResultsByCourse(course);
    }

    // Get results by ID
    @GetMapping("/{id}")
    public ResponseEntity<Results> getResultsById(@PathVariable Long id) {
        try {
            Results results = resultsService.getResultsById(id);
            return ResponseEntity.ok(results);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete results by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResults(@PathVariable Long id) {
        try {
            resultsService.deleteResults(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
