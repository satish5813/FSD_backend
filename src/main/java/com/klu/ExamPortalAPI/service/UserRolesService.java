package com.klu.ExamPortalAPI.service;

import com.klu.ExamPortalAPI.constants.MenuItems;
import com.klu.ExamPortalAPI.model.UserRoles;
import com.klu.ExamPortalAPI.repository.UserRolesRepository;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserRolesService {

    private final UserRolesRepository userRolesRepository;

    public UserRolesService(UserRolesRepository userRolesRepository) {
        this.userRolesRepository = userRolesRepository;
    }

    public UserRoles saveUserRole(UserRoles userRole) {
        return userRolesRepository.save(userRole);
    }

    public Optional<UserRoles> getUserRoleById(Long id) {
        return userRolesRepository.findById(id);
    }

    public Optional<UserRoles> getUserRoleByEmail(String email) {
        return userRolesRepository.findByEmail(email);
    }

    public List<UserRoles> getAllUserRoles() {
        return userRolesRepository.findAll();
    }

    public UserRoles updateUserRole(Long id, UserRoles updatedUserRole) {
        return userRolesRepository.findById(id).map(existingUserRole -> {
            existingUserRole.setName(updatedUserRole.getName() != null ? updatedUserRole.getName() : existingUserRole.getName());
            existingUserRole.setEmail(updatedUserRole.getEmail() != null ? updatedUserRole.getEmail() : existingUserRole.getEmail());
            existingUserRole.setRole(updatedUserRole.getRole() != null ? updatedUserRole.getRole() : existingUserRole.getRole());
            existingUserRole.setPermissions(updatedUserRole.getPermissions() != null ? updatedUserRole.getPermissions() : existingUserRole.getPermissions());
            return userRolesRepository.save(existingUserRole);
        }).orElseThrow(() -> new RuntimeException("UserRole with ID " + id + " not found."));
    }

    public void deleteUserRole(Long id) {
        if (userRolesRepository.existsById(id)) {
            userRolesRepository.deleteById(id);
        } else {
            throw new RuntimeException("UserRole with ID " + id + " does not exist.");
        }
    }

    public List<UserRoles> uploadUserRolesFromCSV(InputStream inputStream) {
        List<UserRoles> userRolesList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            boolean isFirstLine = true; // Skip header
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] fields = line.split(",");
                if (fields.length >= 4) {
                    List<String> permissions = List.of(fields[3].trim().split(";")); // Permissions separated by semicolons
                    UserRoles userRole = UserRoles.builder()
                            .name(fields[0].trim())
                            .email(fields[1].trim())
                            .role(fields[2].trim())
                            .permissions(permissions)
                            .build();
                    userRolesList.add(userRole);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading CSV file: " + e.getMessage());
        }

        return userRolesRepository.saveAll(userRolesList);
    }

    public List<String> getMenuItems() {
        return MenuItems.DEFAULT_MENU_ITEMS;
    }
}
