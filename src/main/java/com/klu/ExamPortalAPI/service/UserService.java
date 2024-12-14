package com.klu.ExamPortalAPI.service;

import com.klu.ExamPortalAPI.model.User;
import com.klu.ExamPortalAPI.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Save a new user with default values for roles and createdAt if not provided
    public User saveUser(User user) {
        if (user.getCreatedAt() == null) {
            user.setCreatedAt(LocalDateTime.now());
        }

        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles("USER");
        }

        return userRepository.save(user);
    }

    // Retrieve a user by username
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Retrieve a user by ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Retrieve all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Delete a user by ID
    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("User with ID " + id + " does not exist.");
        }
    }

    // Update user details
    public User updateUser(Long id, User updatedUser) {
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setUsername(updatedUser.getUsername() != null ? updatedUser.getUsername() : existingUser.getUsername());
            existingUser.setEmail(updatedUser.getEmail() != null ? updatedUser.getEmail() : existingUser.getEmail());
            existingUser.setPassword(updatedUser.getPassword() != null ? updatedUser.getPassword() : existingUser.getPassword());
            existingUser.setMobile(updatedUser.getMobile() != null ? updatedUser.getMobile() : existingUser.getMobile());
            existingUser.setRoles(updatedUser.getRoles() != null ? updatedUser.getRoles() : existingUser.getRoles());
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new RuntimeException("User with ID " + id + " not found."));
    }
}
