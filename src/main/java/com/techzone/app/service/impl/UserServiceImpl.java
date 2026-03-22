package com.techzone.app.service.impl;

import com.techzone.app.entity.AppUser;
import com.techzone.app.repository.RoleRepository;
import com.techzone.app.repository.UserRepository;
import com.techzone.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AppUser registerCustomer(AppUser user) {
        return registerWithRole(user, "CUSTOMER");
    }

    @Override
    public AppUser registerStaff(AppUser user) {
        return registerWithRole(user, "STAFF");
    }

    private AppUser registerWithRole(AppUser user, String roleName) {
        logger.info("📝 Starting registration for user: {} with role: {}", user.getUsername(), roleName);
        
        validateRegistrationInput(user);
        logger.info("✓ Validation passed");

        if (userRepository.existsByUsername(user.getUsername())) {
            logger.warn("❌ Username already exists: {}", user.getUsername());
            throw new IllegalArgumentException("Username đã tồn tại");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            logger.warn("❌ Email already exists: {}", user.getEmail());
            throw new IllegalArgumentException("Email đã tồn tại");
        }
        
        logger.info("✓ Username and email are unique");

        user.setUsername(user.getUsername().trim());
        user.setEmail(user.getEmail().trim().toLowerCase());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        
        logger.info("🔍 Looking for role: {}", roleName);
        user.setRoles(Set.of(roleRepository.findByName(roleName)
                .orElseThrow(() -> {
                    logger.error("❌ Role not found: {}", roleName);
                    return new IllegalStateException("Role '" + roleName + "' không tồn tại trong hệ thống. Vui lòng liên hệ admin.");
                })));
        
        logger.info("✓ Role assigned: {}", roleName);
        logger.info("💾 Saving user to database...");
        
        AppUser savedUser = userRepository.save(user);
        logger.info("✅ User saved successfully with ID: {}", savedUser.getId());
        
        return savedUser;
    }

    private void validateRegistrationInput(AppUser user) {
        if (user == null) {
            throw new IllegalArgumentException("Dữ liệu đăng ký không hợp lệ");
        }
        if (user.getUsername() == null || user.getUsername().trim().length() < 3) {
            throw new IllegalArgumentException("Username phải có ít nhất 3 ký tự");
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email không được để trống");
        }
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new IllegalArgumentException("Mật khẩu phải có ít nhất 6 ký tự");
        }
    }

    @Override
    @PostConstruct
    public void initializeRolesAndAdmin() {
        // Roles and accounts are seeded from db/seed-data.sql to keep initialization filesystem-based.
        // This avoids hardcoding credentials in Java source and allows easier data-driven management.
    }

    @Override
    public AppUser updateEmail(String username, String newEmail) {
        logger.info("📧 Updating email for user: {}", username);
        
        if (newEmail == null || newEmail.trim().isEmpty()) {
            throw new IllegalArgumentException("Email không được để trống");
        }
        
        String trimmedEmail = newEmail.trim().toLowerCase();
        
        // Check if email already exists for another user
        userRepository.findByEmail(trimmedEmail).ifPresent(existingUser -> {
            if (!existingUser.getUsername().equals(username)) {
                logger.warn("❌ Email already exists for another user: {}", trimmedEmail);
                throw new IllegalArgumentException("Email đã được sử dụng bởi tài khoản khác");
            }
        });
        
        AppUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        user.setEmail(trimmedEmail);
        AppUser savedUser = userRepository.save(user);
        
        logger.info("✅ Email updated successfully for user: {}", username);
        return savedUser;
    }

    @Override
    public void changePassword(String username, String oldPassword, String newPassword) {
        logger.info("🔐 Changing password for user: {}", username);
        
        if (newPassword == null || newPassword.length() < 6) {
            throw new IllegalArgumentException("Mật khẩu mới phải có ít nhất 6 ký tự");
        }
        
        AppUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Verify old password
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            logger.warn("❌ Old password does not match for user: {}", username);
            throw new IllegalArgumentException("Mật khẩu hiện tại không đúng");
        }
        
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setRawPassword(newPassword); // Update raw password for reference
        userRepository.save(user);
        
        logger.info("✅ Password changed successfully for user: {}", username);
    }
}
