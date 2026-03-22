package com.techzone.app;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestBcryptCompat {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        // Test if $2b$ hashes work with Spring BCrypt
        String hash2b = "$2b$10$sHdjNvgy.ShUOlvs3./Ot.ZNB76nEZbFQiUAA7KBCC4P8YudLPBZi";
        String hash2a = "$2a$10$sHdjNvgy.ShUOlvs3./Ot.ZNB76nEZbFQiUAA7KBCC4P8YudLPBZi";
        
        System.out.println("Testing password: admin123");
        System.out.println("$2b$ hash matches: " + encoder.matches("admin123", hash2b));
        System.out.println("$2a$ hash matches: " + encoder.matches("admin123", hash2a));
        
        // Generate proper Spring Security BCrypt hashes
        System.out.println("\n=== Proper Spring Security BCrypt Hashes ===");
        System.out.println("admin123: " + encoder.encode("admin123"));
        System.out.println("staff123: " + encoder.encode("staff123"));
        System.out.println("user123: " + encoder.encode("user123"));
        System.out.println("user456: " + encoder.encode("user456"));
    }
}

