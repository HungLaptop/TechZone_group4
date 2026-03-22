package com.techzone.app;

import com.techzone.app.entity.Role;
import com.techzone.app.repository.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;
    private final RoleRepository roleRepository;

    @PostConstruct
    public void init() {
        // Initialize roles first - critical for registration to work
        initializeRoles();
        
        // For local profile (H2), data is seeded through src/main/resources/db/seed-data.sql
        // For other profiles (SQL Server), we need to ensure roles exist
    }
    
    private void initializeRoles() {
        String[] roleNames = {"ADMIN", "STAFF", "CUSTOMER"};
        
        for (String roleName : roleNames) {
            if (roleRepository.findByName(roleName).isEmpty()) {
                Role role = new Role();
                role.setName(roleName);
                roleRepository.save(role);
                System.out.println("✅ Created role: " + roleName);
            }
        }
    }
}
