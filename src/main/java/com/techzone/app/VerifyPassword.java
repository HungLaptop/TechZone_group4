package com.techzone.app;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class VerifyPassword {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        // Test the hashes from seed-data.sql
        String[] hashes = {
            "$2a$10$sHdjNvgy.ShUOlvs3./Ot.ZNB76nEZbFQiUAA7KBCC4P8YudLPBZi", // admin123
            "$2a$10$3SkWhJluyqPHi/rgBaffbeEfNZ9Ugs1.db.yG23V58uaJepUZ8zfq", // staff123
            "$2a$10$JlgDRCG9hXYEaYJ9jOk23.DdTakMpcL.Gq1NvFgSI5EAyxawLvhRy", // user123
            "$2a$10$069Ps5BPYJQ3X3FU8Vdtyeo561QYnHhF2RzBrAJ.uc5nGnmyQOBoa"  // user456
        };
        
        String[] passwords = {"admin123", "staff123", "user123", "user456"};
        String[] users = {"admin", "staff1", "user1", "user2"};
        
        System.out.println("=== Verifying BCrypt Password Matches ===");
        System.out.println();
        
        for (int i = 0; i < hashes.length; i++) {
            boolean matches = encoder.matches(passwords[i], hashes[i]);
            String status = matches ? "✓ OK" : "✗ FAIL";
            System.out.println(status + " - " + users[i] + " / " + passwords[i]);
        }
        
        System.out.println();
        System.out.println("=== Generating Fresh Hashes ===");
        for (int i = 0; i < passwords.length; i++) {
            String newHash = encoder.encode(passwords[i]);
            System.out.println(users[i] + " (" + passwords[i] + "): " + newHash);
        }
    }
}

