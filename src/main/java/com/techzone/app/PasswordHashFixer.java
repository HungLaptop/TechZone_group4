package com.techzone.app;

import com.techzone.app.entity.AppUser;
import com.techzone.app.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Order(100) // Run after seed data is loaded
public class PasswordHashFixer {
    
    private static final Logger logger = LoggerFactory.getLogger(PasswordHashFixer.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @PostConstruct
    public void fixPasswordHashes() {
        logger.info("🔧 Checking and fixing password hashes...");
        
        List<AppUser> users = userRepository.findAll();
        int fixed = 0;
        
        for (AppUser user : users) {
            // If user has a raw password, re-hash it
            if (user.getRawPassword() != null && !user.getRawPassword().isEmpty()) {
                String rawPassword = user.getRawPassword();
                
                // Check if current hash matches the raw password
                boolean currentHashValid = false;
                try {
                    currentHashValid = passwordEncoder.matches(rawPassword, user.getPassword());
                } catch (Exception e) {
                    logger.warn("⚠️  Invalid hash format for user {}", user.getUsername());
                }
                
                if (!currentHashValid) {
                    logger.info("🔨 Re-hashing password for user: {}", user.getUsername());
                    user.setPassword(passwordEncoder.encode(rawPassword));
                    userRepository.save(user);
                    fixed++;
                } else {
                    logger.info("✅ Password hash OK for user: {}", user.getUsername());
                }
            }
        }
        
        if (fixed > 0) {
            logger.info("✅ Fixed {} password hash(es)", fixed);
        } else {
            logger.info("✅ All password hashes are valid");
        }
    }
}

