package com.klu.ExamPortalAPI.controller;

import com.klu.ExamPortalAPI.model.Course;
import com.klu.ExamPortalAPI.service.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // Create a new course
    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        try {
            Course savedCourse = courseService.saveCourse(course);
            return ResponseEntity.ok(savedCourse);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build(); // Handles validation or business logic errors
        }
    }

    // Get all courses
    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    // Get course by ID
    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        return courseService.getCourseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get course by name
    @GetMapping("/name/{name}")
    public ResponseEntity<Course> getCourseByName(@PathVariable String name) {
        return courseService.getCourseByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update a course
    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody Course updatedCourse) {
        try {
            Course course = courseService.updateCourse(id, updatedCourse);
            return ResponseEntity.ok(course);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a course by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        try {
            courseService.deleteCourse(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Bulk upload courses from CSV
    @PostMapping("/upload")
    public ResponseEntity<List<Course>> uploadCourses(@RequestParam("file") MultipartFile file) {
        try {
            List<Course> courses = courseService.uploadCoursesFromCSV(file.getInputStream());
            return ResponseEntity.ok(courses);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(null); // Handles file I/O issues
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null); // Handles CSV parsing or validation errors
        }
    }
}
