package com.zaalima.iam_server.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zaalima.iam_server.entity.User;
import com.zaalima.iam_server.repository.UserRepository;
import com.zaalima.iam_server.dto.UserProfileResponse;

@RestController
public class UserController {


    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @GetMapping("/api/users/me")
    public String getCurrentUser(Authentication authentication) {

        return "Authenticated user: " + authentication.getName();
    }

    @GetMapping("/api/users/profile")
    @PreAuthorize("hasRole('USER')")
    public String userProfile(Authentication authentication) {

        return "ROLE_USER access granted for: "
                + authentication.getName();
    }
    @GetMapping("/api/admin/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminDashboard(Authentication authentication) {
        return "ROLE_ADMIN access granted for: "
                + authentication.getName();
    }
    @GetMapping("/api/users/read")
    @PreAuthorize("hasAuthority('READ_USER')")
    public String readUser(Authentication authentication) {
        return "READ_USER permission granted for: "
                + authentication.getName();
    }

    @GetMapping("/api/client/info")
    public String clientInfo(Authentication authentication) {

        return "Client authenticated: "
                + authentication.getName();
    }

    @GetMapping("/api/users/profile-details")
    public UserProfileResponse getProfile(Authentication authentication) {

        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return new UserProfileResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.isEnabled(),
                user.isMfaEnabled()
        );
    }
}
