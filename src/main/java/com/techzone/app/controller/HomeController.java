package com.techzone.app.controller;

import com.techzone.app.entity.AppUser;
import com.techzone.app.repository.UserRepository;
import com.techzone.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class HomeController {
    
    private final UserRepository userRepository;
    private final UserService userService;

    /**
     * Add authentication info to all pages
     */
    @ModelAttribute
    public void addAuthenticationInfo(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated() 
            && !(authentication instanceof org.springframework.security.authentication.AnonymousAuthenticationToken)) {
            model.addAttribute("isLoggedIn", true);
            model.addAttribute("username", authentication.getName());
            
            // Check roles
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority()));
            boolean isStaff = authentication.getAuthorities().stream()
                    .anyMatch(a -> "ROLE_STAFF".equals(a.getAuthority()));
            boolean isCustomer = authentication.getAuthorities().stream()
                    .anyMatch(a -> "ROLE_CUSTOMER".equals(a.getAuthority()));
            
            model.addAttribute("isAdmin", isAdmin);
            model.addAttribute("isStaff", isStaff);
            model.addAttribute("isCustomer", isCustomer);
        } else {
            model.addAttribute("isLoggedIn", false);
            model.addAttribute("isAdmin", false);
            model.addAttribute("isStaff", false);
            model.addAttribute("isCustomer", false);
        }
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "TechZone 2026");
        return "home";
    }

    @GetMapping("/login")
    public String login(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated() &&
                !(authentication instanceof org.springframework.security.authentication.AnonymousAuthenticationToken)) {
            return "redirect:/";
        }
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "admin/dashboard";
    }

    @GetMapping("/staff/dashboard")
    public String staffDashboard() {
        return "staff/dashboard";
    }

    @GetMapping("/customer/dashboard")
    public String customerDashboard() {
        return "customer/dashboard";
    }

    @GetMapping("/chat")
    public String chat() {
        return "chat";
    }

    @GetMapping("/dashboard/events")
    public String dashboardEvents() {
        return "dashboard-events";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }
    
    @GetMapping("/profile")
    public String profile(Model model, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated() ||
                authentication instanceof org.springframework.security.authentication.AnonymousAuthenticationToken) {
            return "redirect:/login";
        }
        
        String username = authentication.getName();
        AppUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        model.addAttribute("user", user);
        return "profile";
    }
    
    @PostMapping("/profile/update-email")
    public String updateEmail(@RequestParam(name = "newEmail") String newEmail, 
                             Authentication authentication,
                             RedirectAttributes redirectAttributes) {
        try {
            String username = authentication.getName();
            userService.updateEmail(username, newEmail);
            redirectAttributes.addFlashAttribute("successMessage", "Email đã được cập nhật thành công!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra khi cập nhật email");
        }
        return "redirect:/profile";
    }
    
    @PostMapping("/profile/change-password")
    public String changePassword(@RequestParam(name = "oldPassword") String oldPassword,
                                 @RequestParam(name = "newPassword") String newPassword,
                                 @RequestParam(name = "confirmPassword") String confirmPassword,
                                 Authentication authentication,
                                 RedirectAttributes redirectAttributes) {
        try {
            if (!newPassword.equals(confirmPassword)) {
                redirectAttributes.addFlashAttribute("errorMessage", "Mật khẩu mới và xác nhận mật khẩu không khớp");
                return "redirect:/profile";
            }
            
            String username = authentication.getName();
            userService.changePassword(username, oldPassword, newPassword);
            redirectAttributes.addFlashAttribute("successMessage", "Mật khẩu đã được thay đổi thành công!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra khi thay đổi mật khẩu");
        }
        return "redirect:/profile";
    }
}
