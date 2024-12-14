package com.klu.ExamPortalAPI.service;

import com.klu.ExamPortalAPI.model.Course;
import com.klu.ExamPortalAPI.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    public Optional<Course> getCourseByName(String name) {
        return courseRepository.findByName(name);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course updateCourse(Long id, Course updatedCourse) {
        return courseRepository.findById(id).map(existingCourse -> {
            existingCourse.setName(updatedCourse.getName() != null ? updatedCourse.getName() : existingCourse.getName());
            existingCourse.setCode(updatedCourse.getCode() != null ? updatedCourse.getCode() : existingCourse.getCode());
            existingCourse.setDescription(updatedCourse.getDescription() != null ? updatedCourse.getDescription() : existingCourse.getDescription());
            existingCourse.setSchedule(updatedCourse.getSchedule() != null ? updatedCourse.getSchedule() : existingCourse.getSchedule());
            return courseRepository.save(existingCourse);
        }).orElseThrow(() -> new RuntimeException("Course with ID " + id + " not found."));
    }

    public void deleteCourse(Long id) {
        if (courseRepository.existsById(id)) {
            courseRepository.deleteById(id);
        } else {
            throw new RuntimeException("Course with ID " + id + " does not exist.");
        }
    }

    // Bulk upload from CSV file
    public List<Course> uploadCoursesFromCSV(InputStream inputStream) {
        List<Course> courses = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            boolean isFirstLine = true; // Skip the header line
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] fields = line.split(",");
                if (fields.length >= 4) {
                    Course course = Course.builder()
                            .name(fields[0].trim())
                            .code(fields[1].trim())
                            .description(fields[2].trim())
                            .schedule(LocalDate.parse(fields[3].trim()))
                            .build();
                    courses.add(course);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading CSV file: " + e.getMessage());
        }

        return courseRepository.saveAll(courses);
    }
}
