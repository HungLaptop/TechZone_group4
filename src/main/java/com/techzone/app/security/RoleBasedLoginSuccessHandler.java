package com.techzone.app.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RoleBasedLoginSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(RoleBasedLoginSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = "/"; // Redirect to home page after login
        logger.info("🔐 Login successful for: {}", authentication.getName());
        logger.info("📋 Authorities: {}", authentication.getAuthorities());
        logger.info("➡️ Redirecting to home page: {}", targetUrl);
        
        response.sendRedirect(targetUrl);
    }
}
