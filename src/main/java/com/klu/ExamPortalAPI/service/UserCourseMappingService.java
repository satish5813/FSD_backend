package com.klu.ExamPortalAPI.service;

import com.klu.ExamPortalAPI.model.UserCourseMapping;
import com.klu.ExamPortalAPI.repository.UserCourseMappingRepository;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserCourseMappingService {

    private final UserCourseMappingRepository userCourseMappingRepository;

    public UserCourseMappingService(UserCourseMappingRepository userCourseMappingRepository) {
        this.userCourseMappingRepository = userCourseMappingRepository;
    }

    // Save a single mapping
    public UserCourseMapping saveMapping(UserCourseMapping mapping) {
        return userCourseMappingRepository.save(mapping);
    }

    // Get all mappings
    public List<UserCourseMapping> getAllMappings() {
        return userCourseMappingRepository.findAll();
    }

    // Get mappings by user
    public List<UserCourseMapping> getMappingsByUser(Long userId) {
        return userCourseMappingRepository.findByUserId(userId);
    }

    // Get mappings by course
    public List<UserCourseMapping> getMappingsByCourse(Long courseId) {
        return userCourseMappingRepository.findByCourseId(courseId);
    }

    // Update a mapping
    public UserCourseMapping updateMapping(Long id, UserCourseMapping updatedMapping) {
        return userCourseMappingRepository.findById(id).map(existingMapping -> {
            existingMapping.setUserId(updatedMapping.getUserId() != null ? updatedMapping.getUserId() : existingMapping.getUserId());
            existingMapping.setCourseId(updatedMapping.getCourseId() != null ? updatedMapping.getCourseId() : existingMapping.getCourseId());
            return userCourseMappingRepository.save(existingMapping);
        }).orElseThrow(() -> new RuntimeException("Mapping with ID " + id + " not found."));
    }

    // Delete a mapping
    public void deleteMapping(Long id) {
        if (userCourseMappingRepository.existsById(id)) {
            userCourseMappingRepository.deleteById(id);
        } else {
            throw new RuntimeException("Mapping with ID " + id + " not found.");
        }
    }

    // Bulk upload mappings from CSV
    public List<UserCourseMapping> uploadMappingsFromCSV(InputStream inputStream) {
        List<UserCourseMapping> mappings = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            boolean isFirstLine = true; // Skip header
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] fields = line.split(",");
                if (fields.length >= 2) {
                    UserCourseMapping mapping = UserCourseMapping.builder()
                            .userId(Long.parseLong(fields[0].trim()))
                            .courseId(Long.parseLong(fields[1].trim()))
                            .build();
                    mappings.add(mapping);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading CSV file: " + e.getMessage());
        }

        return userCourseMappingRepository.saveAll(mappings);
    }
}
