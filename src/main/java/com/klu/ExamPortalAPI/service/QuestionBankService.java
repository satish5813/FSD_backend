package com.klu.ExamPortalAPI.service;

import com.klu.ExamPortalAPI.model.QuestionBank;
import com.klu.ExamPortalAPI.model.QuestionOption;
import com.klu.ExamPortalAPI.repository.QuestionBankRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

@Service
public class QuestionBankService {

    private final QuestionBankRepository questionBankRepository;

    public QuestionBankService(QuestionBankRepository questionBankRepository) {
        this.questionBankRepository = questionBankRepository;
    }

    // Save a question with options
    public QuestionBank saveQuestion(QuestionBank questionBank) {
        // Ensure each option links back to the QuestionBank
        if (questionBank.getOptions() != null) {
            questionBank.getOptions().forEach(option -> option.setQuestionBank(questionBank));
        }
        return questionBankRepository.save(questionBank); // Use save for a single entity
    }

    // Get all questions
    public List<QuestionBank> getAllQuestions() {
        return questionBankRepository.findAll();
    }

    // Get questions by course ID
    public List<QuestionBank> getQuestionsByCourseId(Long courseId) {
        return questionBankRepository.findByCourseId(courseId);
    }

    // Get question by ID
    public QuestionBank getQuestionById(Long id) {
        return questionBankRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question with ID " + id + " not found."));
    }

    // Update a question
    public QuestionBank updateQuestion(Long id, QuestionBank updatedQuestion) {
        return questionBankRepository.findById(id).map(existingQuestion -> {
            existingQuestion.setCourseId(updatedQuestion.getCourseId() != null ? updatedQuestion.getCourseId() : existingQuestion.getCourseId());
            existingQuestion.setQuestionText(updatedQuestion.getQuestionText() != null ? updatedQuestion.getQuestionText() : existingQuestion.getQuestionText());
            existingQuestion.setCorrectAnswer(updatedQuestion.getCorrectAnswer() != null ? updatedQuestion.getCorrectAnswer() : existingQuestion.getCorrectAnswer());

            if (updatedQuestion.getOptions() != null) {
                existingQuestion.getOptions().clear();
                updatedQuestion.getOptions().forEach(option -> option.setQuestionBank(existingQuestion));
                existingQuestion.getOptions().addAll(updatedQuestion.getOptions());
            }

            // Corrected: Use the injected repository instance
            return questionBankRepository.save(existingQuestion);
        }).orElseThrow(() -> new RuntimeException("Question with ID " + id + " not found."));
    }

    // Delete a question
    public void deleteQuestion(Long id) {
        if (questionBankRepository.existsById(id)) {
            questionBankRepository.deleteById(id);
        } else {
            throw new RuntimeException("Question with ID " + id + " not found.");
        }
    }
    
    public List<QuestionBank> uploadQuestionsFromCSV(InputStream inputStream) {
        List<QuestionBank> questionBanks = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            boolean isFirstLine = true; // Skip the header row
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] fields = line.split(",");
                if (fields.length >= 4) {
                    Long courseId = Long.parseLong(fields[0].trim());
                    String questionText = fields[1].trim();
                    String[] optionsArray = fields[2].trim().split("\\|"); // Options separated by |
                    String correctAnswer = fields[3].trim();

                    List<QuestionOption> options = new ArrayList<>();
                    for (String option : optionsArray) {
                        options.add(QuestionOption.builder().optionText(option).build());
                    }

                    QuestionBank questionBank = QuestionBank.builder()
                            .courseId(courseId)
                            .questionText(questionText)
                            .options(options)
                            .correctAnswer(correctAnswer)
                            .build();

                    // Set QuestionBank reference in each option
                    options.forEach(option -> option.setQuestionBank(questionBank));

                    questionBanks.add(questionBank);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading CSV file: " + e.getMessage());
        }

        return questionBankRepository.saveAll(questionBanks);
    }
}
