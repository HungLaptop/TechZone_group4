package com.techzone.app.controller;

import com.techzone.app.dto.RegistrationRequest;
import com.techzone.app.entity.AppUser;
import com.techzone.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final UserService userService;

    @PostMapping("/register")
    public String doRegister(@ModelAttribute RegistrationRequest request,
                             RedirectAttributes redirectAttributes) {

        String username = request.getUsername();
        String email = request.getEmail();
        String password = request.getPassword();
        String confirmPassword = request.getConfirmPassword();

        if (!StringUtils.hasText(username) || !StringUtils.hasText(email) || !StringUtils.hasText(password)) {
            redirectAttributes.addFlashAttribute("error", "Vui lòng nhập đầy đủ thông tin.");
            redirectAttributes.addFlashAttribute("username", username);
            redirectAttributes.addFlashAttribute("email", email);
            return "redirect:/register";
        }
        if (confirmPassword != null && !password.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Mật khẩu xác nhận không khớp.");
            redirectAttributes.addFlashAttribute("username", username);
            redirectAttributes.addFlashAttribute("email", email);
            return "redirect:/register";
        }

        AppUser user = AppUser.builder()
                .username(username.trim())
                .email(email.trim().toLowerCase())
                .password(password)
                .build();

        try {
            logger.info("📝 Attempting to register user: {}", username);
            AppUser savedUser = userService.registerCustomer(user);
            logger.info("✅ User registered successfully: {} (ID: {})", savedUser.getUsername(), savedUser.getId());
            redirectAttributes.addFlashAttribute("success", "Đăng ký thành công! Hãy đăng nhập với tài khoản: " + username);
            return "redirect:/login";
        } catch (Exception ex) {
            logger.error("❌ Registration failed for user {}: {}", username, ex.getMessage(), ex);
            redirectAttributes.addFlashAttribute("error", "Lỗi: " + ex.getMessage());
            redirectAttributes.addFlashAttribute("username", username);
            redirectAttributes.addFlashAttribute("email", email);
            return "redirect:/register";
        }
    }


}
