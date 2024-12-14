package com.klu.ExamPortalAPI.controller;

import com.klu.ExamPortalAPI.model.UserCourseMapping;
import com.klu.ExamPortalAPI.service.UserCourseMappingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/user-course-mappings")
public class UserCourseMappingController {

    private final UserCourseMappingService userCourseMappingService;

    public UserCourseMappingController(UserCourseMappingService userCourseMappingService) {
        this.userCourseMappingService = userCourseMappingService;
    }

    // Create a single mapping
    @PostMapping
    public ResponseEntity<UserCourseMapping> createMapping(@RequestBody UserCourseMapping mapping) {
        UserCourseMapping savedMapping = userCourseMappingService.saveMapping(mapping);
        return ResponseEntity.ok(savedMapping);
    }

    // Get all mappings
    @GetMapping
    public List<UserCourseMapping> getAllMappings() {
        return userCourseMappingService.getAllMappings();
    }

    // Get mappings by user
    @GetMapping("/user/{userId}")
    public List<UserCourseMapping> getMappingsByUser(@PathVariable Long userId) {
        return userCourseMappingService.getMappingsByUser(userId);
    }

    // Get mappings by course
    @GetMapping("/course/{courseId}")
    public List<UserCourseMapping> getMappingsByCourse(@PathVariable Long courseId) {
        return userCourseMappingService.getMappingsByCourse(courseId);
    }

    // Update a mapping
    @PutMapping("/{id}")
    public ResponseEntity<UserCourseMapping> updateMapping(@PathVariable Long id, @RequestBody UserCourseMapping updatedMapping) {
        try {
            UserCourseMapping mapping = userCourseMappingService.updateMapping(id, updatedMapping);
            return ResponseEntity.ok(mapping);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a mapping
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMapping(@PathVariable Long id) {
        try {
            userCourseMappingService.deleteMapping(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Bulk upload mappings
    @PostMapping("/upload")
    public ResponseEntity<List<UserCourseMapping>> uploadMappings(@RequestParam("file") MultipartFile file) throws IOException {
        List<UserCourseMapping> mappings = userCourseMappingService.uploadMappingsFromCSV(file.getInputStream());
        return ResponseEntity.ok(mappings);
    }
}
