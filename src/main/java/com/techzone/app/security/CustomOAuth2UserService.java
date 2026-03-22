package com.techzone.app.security;

import com.techzone.app.entity.AppUser;
import com.techzone.app.entity.Role;
import com.techzone.app.repository.RoleRepository;
import com.techzone.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);

        String email = oauth2User.getAttribute("email");

        userRepository.findByEmail(email).orElseGet(() -> {
            Role customerRole = roleRepository.findByName("CUSTOMER").orElseThrow();
            AppUser newUser = AppUser.builder()
                    .username(email)
                    .email(email)
                    .password("oauth2-user")
                    .enabled(true)
                    .roles(new HashSet<>(Set.of(customerRole)))
                    .build();
            return userRepository.save(newUser);
        });

        return oauth2User;
    }
}
