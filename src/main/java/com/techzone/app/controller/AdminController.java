package com.techzone.app.controller;

import com.techzone.app.entity.AppUser;
import com.techzone.app.entity.Role;
import com.techzone.app.repository.RoleRepository;
import com.techzone.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/manage/users")
    public String manageUsers(Model model) {
        List<AppUser> users = userRepository.findAll();
        model.addAttribute("users", users);
        model.addAttribute("roles", roleRepository.findAll());
        return "admin/manage/users";
    }

    @PostMapping("/manage/users")
    public String createUser(@RequestParam String username,
                             @RequestParam String email,
                             @RequestParam String password,
                             @RequestParam(required = false) List<Long> roleIds) {
        AppUser user = AppUser.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .rawPassword(password)
                .enabled(true)
                .build();

        Set<Role> roleSet = new HashSet<>();
        if (roleIds != null) {
            for (Long roleId : roleIds) {
                roleRepository.findById(roleId).ifPresent(roleSet::add);
            }
        }
        user.setRoles(roleSet);
        userRepository.save(user);
        return "redirect:/admin/manage/users";
    }

    @GetMapping("/manage/users/delete/{userId}")
    public String deleteUser(@PathVariable Long userId) {
        userRepository.deleteById(userId);
        return "redirect:/admin/manage/users";
    }
}
