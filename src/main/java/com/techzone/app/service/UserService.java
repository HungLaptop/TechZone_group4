package com.techzone.app.service;

import com.techzone.app.entity.AppUser;

public interface UserService {
    AppUser registerCustomer(AppUser user);
    AppUser registerStaff(AppUser user);
    void initializeRolesAndAdmin();
    AppUser updateEmail(String username, String newEmail);
    void changePassword(String username, String oldPassword, String newPassword);
}
