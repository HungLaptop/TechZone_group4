package com.techzone.app.security;

import com.techzone.app.entity.AppUser;
import com.techzone.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("🔍 Loading user: {}", username);
        AppUser appUser = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    logger.error("❌ User not found: {}", username);
                    return new UsernameNotFoundException("User not found: " + username);
                });

        logger.info("✓ User found: {}", username);
        logger.info("✓ Enabled: {}", appUser.isEnabled());
        logger.info("✓ Roles: {}", appUser.getRoles());
        logger.info("✓ Password hash starts with: {}", appUser.getPassword().substring(0, Math.min(15, appUser.getPassword().length())));

        return new org.springframework.security.core.userdetails.User(
                appUser.getUsername(),
                appUser.getPassword(),
                appUser.isEnabled(),
                true,
                true,
                true,
                appUser.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().toUpperCase()))
                        .collect(Collectors.toSet()));
    }
}
