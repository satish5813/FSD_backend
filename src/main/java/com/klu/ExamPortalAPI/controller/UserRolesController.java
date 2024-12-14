package com.klu.ExamPortalAPI.controller;

import com.klu.ExamPortalAPI.model.UserRoles;
import com.klu.ExamPortalAPI.service.UserRolesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/user-roles")
public class UserRolesController {

    private final UserRolesService userRolesService;

    public UserRolesController(UserRolesService userRolesService) {
        this.userRolesService = userRolesService;
    }

    @PostMapping
    public ResponseEntity<UserRoles> createUserRole(@RequestBody UserRoles userRole) {
        UserRoles savedUserRole = userRolesService.saveUserRole(userRole);
        return ResponseEntity.ok(savedUserRole);
    }

    @GetMapping
    public List<UserRoles> getAllUserRoles() {
        return userRolesService.getAllUserRoles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserRoles> getUserRoleById(@PathVariable Long id) {
        return userRolesService.getUserRoleById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserRoles> getUserRoleByEmail(@PathVariable String email) {
        return userRolesService.getUserRoleByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserRoles> updateUserRole(@PathVariable Long id, @RequestBody UserRoles updatedUserRole) {
        try {
            UserRoles userRole = userRolesService.updateUserRole(id, updatedUserRole);
            return ResponseEntity.ok(userRole);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserRole(@PathVariable Long id) {
        try {
            userRolesService.deleteUserRole(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<List<UserRoles>> uploadUserRoles(@RequestParam("file") MultipartFile file) throws IOException {
        try {
            List<UserRoles> userRolesList = userRolesService.uploadUserRolesFromCSV(file.getInputStream());
            return ResponseEntity.ok(userRolesList);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/menus")
    public List<String> getDefaultMenuItems() {
        return userRolesService.getMenuItems();
    }
}
