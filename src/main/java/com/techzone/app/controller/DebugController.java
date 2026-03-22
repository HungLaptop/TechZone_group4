package com.techzone.app.controller;

import com.techzone.app.entity.AppUser;
import com.techzone.app.repository.UserRepository;
import com.techzone.app.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/debug")
@RequiredArgsConstructor
public class DebugController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * List all users (without sensitive data)
     * Access: http://localhost:8081/api/debug/users
     */
    @GetMapping("/users")
    public Map<String, Object> getAllUsers() {
        List<Map<String, Object>> userList = userRepository.findAll().stream()
                .map(user -> {
                    Map<String, Object> userData = new HashMap<>();
                    userData.put("id", user.getId());
                    userData.put("username", user.getUsername());
                    userData.put("email", user.getEmail());
                    userData.put("enabled", user.isEnabled());
                    userData.put("roles", user.getRoles().stream()
                            .map(role -> role.getName())
                            .collect(Collectors.toList()));
                    userData.put("passwordHashPrefix", user.getPassword().substring(0, Math.min(20, user.getPassword().length())));
                    userData.put("hasRawPassword", user.getRawPassword() != null && !user.getRawPassword().isEmpty());
                    return userData;
                })
                .collect(Collectors.toList());
        
        Map<String, Object> response = new HashMap<>();
        response.put("totalUsers", userList.size());
        response.put("users", userList);
        return response;
    }

    /**
     * List all roles
     * Access: http://localhost:8081/api/debug/roles
     */
    @GetMapping("/roles")
    public Map<String, Object> getAllRoles() {
        Map<String, Object> response = new HashMap<>();
        response.put("roles", roleRepository.findAll().stream()
                .map(role -> {
                    Map<String, Object> roleData = new HashMap<>();
                    roleData.put("id", role.getId());
                    roleData.put("name", role.getName());
                    return roleData;
                })
                .collect(Collectors.toList()));
        return response;
    }

    /**
     * Test password for a specific user
     * Access: http://localhost:8081/api/debug/test-login/admin/admin123
     */
    @GetMapping("/test-login/{username}/{password}")
    public Map<String, Object> testLogin(
            @PathVariable("username") String username, 
            @PathVariable("password") String password) {
        
        Map<String, Object> result = new HashMap<>();
        result.put("username", username);
        
        AppUser user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            result.put("found", false);
            result.put("message", "User not found in database");
            return result;
        }
        
        result.put("found", true);
        result.put("enabled", user.isEnabled());
        result.put("passwordHashPrefix", user.getPassword().substring(0, Math.min(20, user.getPassword().length())));
        result.put("passwordMatches", passwordEncoder.matches(password, user.getPassword()));
        result.put("roles", user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList()));
        
        if (passwordEncoder.matches(password, user.getPassword())) {
            result.put("status", "✅ LOGIN OK - Password is correct!");
        } else {
            result.put("status", "❌ LOGIN FAILED - Password does not match!");
        }
        
        return result;
    }
    
    /**
     * Database status check
     * Access: http://localhost:8081/api/debug/status
     */
    @GetMapping("/status")
    public Map<String, Object> getStatus() {
        Map<String, Object> status = new HashMap<>();
        status.put("totalUsers", userRepository.count());
        status.put("totalRoles", roleRepository.count());
        status.put("usersWithRawPassword", userRepository.findAll().stream()
                .filter(u -> u.getRawPassword() != null && !u.getRawPassword().isEmpty())
                .count());
        return status;
    }
}


